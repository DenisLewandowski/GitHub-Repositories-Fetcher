FROM amazoncorretto:17.0.9

WORKDIR /app

COPY ./build/libs/app-*.jar ./app/app.jar

ENTRYPOINT [ "java", "-jar", "./app/app.jar" ]