FROM openjdk:22-jdk
#ARG JAR_FILE=build/libs/backend-ujian-spring-boot-java-0.0.1-SNAPSHOT.jar
COPY build/libs/backend-ujian-spring-boot-java-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]