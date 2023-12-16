FROM amazoncorretto:17.0.9

COPY ./build/app.jar .

ENTRYPOINT ["java","-jar","/app.jar"]