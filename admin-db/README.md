# admin-db
개발용 DB 폴더

## extract-bin.sh
- binlog를 sql파일로 추출하는 쉘 스크립트
- 추출된 sql파일은 docker-data 폴더에 생성된다.

## init-db.sh
- 프로젝트의 최초 기동 시, DB를 초기화하는 쉘 스크립트

## mariadb.Dockerfile
- local 환경, develop 서버, staging 서버에서 사용되는 db docker image의 dockerfile이다.

## initdb/
- 프로젝트에 필요한 DDL, DML을 포함한 sql 모음
- 프로젝트의 최초 기동 시, 한번만 자동 실행된다.
