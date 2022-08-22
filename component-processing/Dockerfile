FROM openjdk:11
ADD target/component-processing-0.0.1-SNAPSHOT.jar component-processing-0.0.1-SNAPSHOT.jar
EXPOSE 8001
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar component-processing-0.0.1-SNAPSHOT.jar"]
