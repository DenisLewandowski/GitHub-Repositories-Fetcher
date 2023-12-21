#!/bin/bash

./gradlew build -p app -x test
docker build -t github-repo-app ./app
docker-compose up -d
