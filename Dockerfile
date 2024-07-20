FROM amazoncorretto:22
LABEL authors="egorm"
COPY ./target/*.jar ./app/main.jar
WORKDIR ./app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "main.jar"]
