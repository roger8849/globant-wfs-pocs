#!/usr/bin/env sh

touch /app.jar

java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
