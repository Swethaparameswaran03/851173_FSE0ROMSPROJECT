FROM openjdk:11
EXPOSE 8100
ADD "target/*.jar" "packdeli.jar"
ENTRYPOINT [ "java", "-jar", "/packdeli.jar" ]