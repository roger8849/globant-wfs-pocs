FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Juan Krzemien

VOLUME /tmp

ADD target/microservice-cassandra-1.0-SNAPSHOT.jar app.jar

ADD docker_start.sh docker_start.sh

ENTRYPOINT ["./docker_start.sh"]