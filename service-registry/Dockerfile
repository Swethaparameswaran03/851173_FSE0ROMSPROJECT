FROM openjdk:11
EXPOSE 8761
ADD "target/*.jar" "servicereg.jar"
ENTRYPOINT [ "java", "-jar", "/servicereg.jar" ]