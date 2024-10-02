# open jdk 17 버전의 환경을 구성한다.
FROM amazoncorretto:17

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profile:s.active=prod", "/app.jar"]