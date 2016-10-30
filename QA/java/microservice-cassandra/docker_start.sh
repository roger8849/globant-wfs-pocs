#!/usr/bin/env sh

touch /app.jar

cat /etc/hosts
cat /etc/resolv.conf

java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
