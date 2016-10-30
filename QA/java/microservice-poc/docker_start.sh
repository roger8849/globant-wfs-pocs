#!/usr/bin/env sh

touch /app.jar

while ! nc ${POSTGRES_HOST_DOCKER} ${POSTGRES_PORT_DOCKER}
do
  echo "$(date) - still trying for ${POSTGRES_HOST_DOCKER}:${POSTGRES_PORT_DOCKER}..."
  sleep 1
done
echo "$(date) - connected successfully"

java -Dspring.datasource.url=jdbc:postgresql://${POSTGRES_URI_DOCKER}/samples -Djava.security.egd=file:/dev/./urandom -jar /app.jar
