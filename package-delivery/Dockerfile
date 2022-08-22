FROM openjdk:11
ADD target/package-delivery-0.0.1-SNAPSHOT.jar package-delivery-0.0.1-SNAPSHOT.jar
EXPOSE 8100
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar package-delivery-0.0.1-SNAPSHOT.jar"]
