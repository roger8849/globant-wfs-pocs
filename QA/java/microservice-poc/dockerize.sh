#!/usr/bin/env bash

docker build -t microservice-poc .

docker run -p 8080:8080 -ti microservice-poc