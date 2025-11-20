FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/*.jar app.jar

# 데이터 저장 폴더 생성
RUN mkdir -p /data

VOLUME ["/data"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
