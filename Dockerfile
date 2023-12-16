FROM amazoncorretto:17.0.9

WORKDIR /app

COPY ./build/libs/github-repo-app-*.jar ./app/github-repo-app.jar

ENTRYPOINT [ "java", "-jar", "./app/github-repo-app.jar" ]