FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Juan Krzemien

VOLUME /tmp

ADD microservice-component-poc-1.0-SNAPSHOT.jar app.jar

ADD docker_start.sh docker_start.sh

EXPOSE 8080

ENTRYPOINT ["./docker_start.sh"]