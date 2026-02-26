# Étape 1 : Build avec Java 21
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
RUN apk add --no-cache maven
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/api-gateway-*.jar app.jar

# Le port d'entrée de ton écosystème
EXPOSE 8765

# Optimisation mémoire
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]