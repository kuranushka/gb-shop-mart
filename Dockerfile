FROM openjdk:11.0.14.1-jre
WORKDIR /app/gb-shop-mart
COPY build/libs/gb-shop-mart-0.0.1-SNAPSHOT.jar gb-shop-mart.jar
COPY src/main/resources/data.sql data.sql
COPY src/main/resources/schema.sql schema.sql