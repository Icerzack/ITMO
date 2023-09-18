FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 24843
ENTRYPOINT ["java", "-jar", "/app/*.jar"]