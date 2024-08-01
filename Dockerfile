FROM gradle:latest AS build
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build

FROM openjdk:17-jdk-slim
COPY --from=build /home/gradle/project/application/build/libs/agendapro_products.jar /home/gradle/project/app.jar
WORKDIR /home/gradle/project
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]



