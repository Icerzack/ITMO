FROM openjdk:17
ADD target/routes-1.jar routes.jar
ADD src/main/resources/keystore.p12 keystore.p12
ENTRYPOINT ["java", "-jar", "routes.jar"]