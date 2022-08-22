FROM openjdk:11
ADD target/service-registry-0.0.1-SNAPSHOT.jar service-registry-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar service-registry-0.0.1-SNAPSHOT.jar"]
