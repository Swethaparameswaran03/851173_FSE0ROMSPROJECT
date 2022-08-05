FROM openjdk:11
EXPOSE 8001
ADD "target/*.jar" "comp.jar"
ENTRYPOINT [ "java", "-jar", "/comp.jar" ]