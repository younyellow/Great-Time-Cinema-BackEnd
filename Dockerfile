FROM openjdk:oracle
RUN mkdir /my-app
WORKDIR /my-app
RUN mkdir /image
COPY ./build/libs/movie-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]