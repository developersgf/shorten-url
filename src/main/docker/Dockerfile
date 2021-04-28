FROM adoptopenjdk/openjdk11:alpine

RUN apk update && apk upgrade && apk add htop
RUN alias ll='ls -al'

RUN mkdir -p /usr/local/shorten-url
RUN chmod a+w -R /usr/local/shorten-url

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp

EXPOSE 8080

# The application's jar file
ARG JAR_FILE=./shorten-url-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} /usr/local/shorten-url/app.jar
ENTRYPOINT ["java", "-jar", "/usr/local/shorten-url/app.jar"]
