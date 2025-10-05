# Etapa 1: Build con Maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiamos el pom primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copiamos el resto del código 
COPY src ./src

# Compilamos
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final ligera con JRE
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
