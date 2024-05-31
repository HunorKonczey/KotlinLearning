FROM gradle:latest AS build

COPY gradlew .
COPY gradle gradle

COPY src src
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew build --no-daemon

FROM openjdk:16
WORKDIR /firstkotlin

COPY --from=build /firstkotlin/build/libs/firstkotlin-0.0.1-SNAPSHOT.jar firstkotlin.jar
EXPOSE 9000
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "firstkotlin.jar"]