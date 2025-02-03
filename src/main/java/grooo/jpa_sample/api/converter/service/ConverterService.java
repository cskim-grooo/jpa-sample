package grooo.jpa_sample.api.converter.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import grooo.jpa_sample.api.converter.domain.Language;
import grooo.jpa_sample.api.converter.dto.ResponseFile;
import grooo.jpa_sample.api.converter.dto.ResponseImport;
import grooo.jpa_sample.api.converter.repository.LanguageRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final LanguageRepository languageRepository;

    public ResponseFile exportLanguages() {

        List<Language> languages = languageRepository.findAll();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        // TODO : EasyExcel 데이터 모델을 자동으로 변환하여 Excel 시트를 생성 ( 개별적으로 셀을 조작하기에 제한적임(Apache POI 일부기능 사용). 대용량 엑셀 처리 빠름. )
        EasyExcel.write(os, Language.class).
                relativeHeadRowIndex(1).
                registerWriteHandler(getSheetMergeHandler()).
                registerWriteHandler(getEasyExcelCustomCellWriteHandler()).
                sheet("easyExcel-시트명").
                doWrite(languages);

        // TODO : Apache POI : 개별 좌표(x, y) 지정 후 셀 입력 ( 모든 조작이 자유롭게 가능하지만 메모리사용량 많고 느리다. )
        writeApachePOIExcel(os, "apachePOI-시트명", languages);

        return new ResponseFile("export_languages.xlsx", os.toByteArray());
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

    public void writeApachePOIExcel(ByteArrayOutputStream os, String sheetName, List<Language> languages) {
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

    public List<ResponseImport> xlsxToJsonFormLanguages(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            List<Map<Integer, String>> rawData = EasyExcel.read(inputStream)
                    .sheet(0)
                    .headRowNumber(1)
                    .doReadSync();
            return convertToJson(rawData);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.UNHANDLED_SERVER_ERROR);
        }
    }

    private List<ResponseImport> convertToJson(List<Map<Integer, String>> rawData) {
        List<ResponseImport> result = new ArrayList<>();
        if (rawData.isEmpty()) return result;

        Map<Integer, String> headerRow = rawData.getFirst();
        List<String> headers = new ArrayList<>(headerRow.values());

        for (int i = 1; i < rawData.size(); i++) {
            Map<Integer, String> rowData = rawData.get(i);
            ResponseImport responseImport = new ResponseImport();

            for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                String cellValue = rowData.getOrDefault(colIndex, "");
                switch (headers.get(colIndex)) {
                    case "id" -> responseImport.setId(parseLong(cellValue));
                    case "name" -> responseImport.setName(cellValue);
                    case "code" -> responseImport.setCode(cellValue);
                    case "lcid" -> responseImport.setLcid(cellValue);
                }
            }
            result.add(responseImport);
        }
        return result;
    }
}
