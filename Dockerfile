# Firstly Build the self-contained JAR with Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the JAR in a lightweight image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the shaded self-contained JAR from the build stage.
# maven-shade-plugin produces a JAR with the Main-Class already set
# in the manifest, so we don't need to name the main class here.
COPY --from=build /app/target/*.jar app.jar

# world-report-system is a CLI/report tool, not a web server,
# so no EXPOSE is needed unless you add a REST API later.

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]