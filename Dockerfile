FROM maven
WORKDIR ./app
COPY . ./app/.
RUN mvn -f /app/pom.xml clean install -Dmaven.test.skip=true

FROM amazoncorretto:22
LABEL authors="egorm"
COPY ./target/*.jar ./app/main.jar
WORKDIR ./app
EXPOSE 80
ENTRYPOINT ["java", "-jar", "main.jar"]
