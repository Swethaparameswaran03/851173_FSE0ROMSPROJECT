/*FROM openjdk:11
EXPOSE 8080
ADD "target/*.jar" "app.jar"
ENTRYPOINT [ "java", "-jar", "/app.jar" ]*/
FROM openjdk:11
ADD target/apigateway-0.0.1-SNAPSHOT.jar apigateway-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar apigateway-0.0.1-SNAPSHOT.jar"]
