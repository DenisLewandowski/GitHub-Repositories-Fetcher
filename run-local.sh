#!/bin/bash

./gradlew build
docker build -t github-repo-app .
docker-compose up -d
