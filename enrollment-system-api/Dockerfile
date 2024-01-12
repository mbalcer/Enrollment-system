FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app/be

EXPOSE 8000

ARG JAR_FILE=target/enrollment-system-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]