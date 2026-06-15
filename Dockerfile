# --- Stage 1: Compile the Java Code ---
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# --- Stage 2: Run Tomcat 11 (Fixed Docker Hub Tag Alignment) ---
FROM tomcat:11.0-jdk17-temurin-jammy
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy the freshly compiled WAR file from Stage 1
COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]
