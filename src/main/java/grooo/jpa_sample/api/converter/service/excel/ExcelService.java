package grooo.jpa_sample.api.converter.service.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import grooo.jpa_sample.api.converter.domain.Language;
import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelService {

    public void createEasyExcel(ByteArrayOutputStream os, List<Language> languages) {
        EasyExcel.write(os, Language.class)
                .relativeHeadRowIndex(1)
                .registerWriteHandler(getSheetMergeHandler())
                .registerWriteHandler(getEasyExcelCustomCellWriteHandler())
                .sheet("EasyExcel-시트")
                .doWrite(languages);
    }

    public void writeExcelWithApachePOI(ByteArrayOutputStream os, String sheetName, List<Language> languages) {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.createSheet(sheetName);
            sheet.setColumnWidth(1, 5000);

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // 헤더 추가
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "언어명", "코드"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 데이터 추가
            int rowIndex = 1;
            for (Language language : languages) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(language.getId());
                row.createCell(1).setCellValue(language.getName());
                row.createCell(2).setCellValue(language.getCode());
            }

            os.reset();
            workbook.write(os);
            workbook.close();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.UNHANDLED_SERVER_ERROR);
        }
    }

    private SheetWriteHandler getSheetMergeHandler() {
        return new SheetWriteHandler() {
            @Override
            public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
                Sheet sheet = writeSheetHolder.getSheet();
                Workbook workbook = writeWorkbookHolder.getWorkbook();

                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                sheet.setColumnWidth(1, 5000);

                Row row = sheet.getRow(0);
                if (row == null) { row = sheet.createRow(0);}
                Cell cell = row.createCell(0);
                cell.setCellValue("작성 날짜: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setFontHeightInPoints((short) 15);
                cellStyle.setFont(font);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cell.setCellStyle(cellStyle);
            }
        };
    }

    private CellWriteHandler getEasyExcelCustomCellWriteHandler() {
        return new CellWriteHandler() {
            @Override
            public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
                if (cell == null || cellDataList == null || cellDataList.isEmpty()) return;

                int rowIndex = cell.getRowIndex();
                int columnIndex = cell.getColumnIndex();
                WriteCellData<?> cellData = cellDataList.getFirst();
                WriteCellStyle writeCellStyle = new WriteCellStyle();
                WriteFont writeFont = new WriteFont();

                writeFont.setFontHeightInPoints((short) 12);
                writeCellStyle.setBorderBottom(BorderStyle.THIN);
                writeCellStyle.setBorderTop(BorderStyle.THIN);
                writeCellStyle.setBorderLeft(BorderStyle.THIN);
                writeCellStyle.setBorderRight(BorderStyle.THIN);
                writeCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);

                if (isHead) {
                    writeFont.setBold(true); // 굵은 글씨 적용
                    writeFont.setFontHeightInPoints((short) 20); // 글씨 크기
                    writeFont.setColor(IndexedColors.BLUE.getIndex()); // 글씨색 파랑

                    writeCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

                    writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                } else if (rowIndex == 2 && columnIndex == 1) { // 특정 셀 (B3) 스타일 적용
                    writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                    writeCellStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                }

                writeCellStyle.setWriteFont(writeFont);
                cellData.setWriteCellStyle(writeCellStyle);
            }
        };
    }

    public List<Map<Integer, String>> readExcelAsRawData(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            return EasyExcel.read(inputStream)
                    .sheet(0)
                    .headRowNumber(1)
                    .doReadSync();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.UNHANDLED_SERVER_ERROR);
        }
    }
}
