FROM adoptopenjdk/openjdk15:alpine

VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE prod

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]