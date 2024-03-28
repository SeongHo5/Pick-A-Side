FROM openjdk:17-alpine
LABEL name="PickSide", author="SeongHo5"
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
