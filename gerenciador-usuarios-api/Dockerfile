FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
ARG CACHE_BUST=1
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/gerenciador-usuarios-api.jar /app/gerenciador-usuarios-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gerenciador-usuarios-api.jar"]
