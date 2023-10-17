FROM gradle:latest AS BUILD
RUN gradle build
RUN gradle bootJar

FROM openjdk:16
ADD /build/libs/firstkotlin-0.0.1-SNAPSHOT.jar firstkotlin.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "firstkotlin.jar"]