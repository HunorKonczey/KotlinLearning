FROM openjdk:16
WORKDIR /firstkotlin
COPY /build/libs/firstkotlin-0.0.1-SNAPSHOT.jar firstkotlin.jar
EXPOSE 9000
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "firstkotlin.jar"]