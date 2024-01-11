FROM amazoncorretto:21-alpine-jdk
COPY ./build/libs/* ./app.jar
ENTRYPOINT ["java","-jar","/app.jar"]