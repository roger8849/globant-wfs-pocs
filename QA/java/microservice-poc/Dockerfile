FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Juan Krzemien

VOLUME /tmp

ADD target/microservice-poc-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]