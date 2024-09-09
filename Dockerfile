FROM openjdk:22-slim-bullseye

# Set the default timezone to GMT+7
ENV TZ=Asia/Jakarta

# Install tzdata package to handle timezone configuration
RUN apt-get update && \
    apt-get install -y tzdata && \
    rm -rf /var/lib/apt/lists/* && \
    ln -fs /usr/share/zoneinfo/Asia/Jakarta /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

#ARG JAR_FILE=build/libs/backend-ujian-spring-boot-java-0.0.1-SNAPSHOT.jar
COPY build/libs/backend-ujian-spring-boot-java-0.0.1-SNAPSHOT.jar app.jar

# Set the timezone for Java application
ENV JAVA_OPTS="-Duser.timezone=Asia/Jakarta"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]