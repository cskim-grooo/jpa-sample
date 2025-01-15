# 1. Gradle 빌드 단계
FROM openjdk:21-jdk AS build

WORKDIR /app

RUN microdnf install -y findutils

# 프로젝트 파일 복사
COPY . .

# Gradle Wrapper 실행 권한 추가
RUN chmod +x ./gradlew

# JAR 파일 빌드
RUN ./gradlew clean bootJar --stacktrace --info

# 2. 실행 단계
FROM openjdk:21-jdk

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# JAR 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
