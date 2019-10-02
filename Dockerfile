FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG PROFILE
ARG CONFIG_URL

ENV CONFIG_URL=$CONFIG_URL
ENV PROFILE=$PROFILE
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/app.jar"]
