#!/bin/bash

./gradlew build -x test
docker build -t github-repo-app .
docker-compose up -d
