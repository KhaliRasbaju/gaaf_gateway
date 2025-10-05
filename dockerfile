# Etapa 1: Build con Maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Forzar siempre limpieza y recompilación (sin usar cache)
RUN mvn clean install -DskipTests

# Etapa 2: Imagen final ligera con JDK 21
FROM eclipse-temurin:21-jre

# Directorio de la aplicación
WORKDIR /app


# Copiamos el jar construido desde el builder
COPY --from=builder /app/target/*.jar app.jar


# Exponer puerto
EXPOSE 8080


# Comando de ejecución
ENTRYPOINT ["java","-jar","/app/app.jar"]
	
	