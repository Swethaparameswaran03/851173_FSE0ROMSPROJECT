FROM openjdk:11
ADD target/authentication-microservice-0.0.1-SNAPSHOT.jar authentication-microservice-0.0.1-SNAPSHOT.jar
EXPOSE 8000
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar authentication-microservice-0.0.1-SNAPSHOT.jar"]
