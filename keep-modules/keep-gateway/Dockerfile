FROM openjdk:8-jre-alpine
ADD target/keep-gateway.jar target/keep-gateway.jar
EXPOSE 6060
ENTRYPOINT ["java", "-jar","-Xmx2048m","-Xms128m", "target/keep-gateway.jar"]