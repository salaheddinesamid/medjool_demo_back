FROM openjdk:17-jdk



WORKDIR app/

COPY target/medjool-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","app","/app/app.jar"]