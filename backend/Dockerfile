# ============================================
# ETAPA 1: Construcción (Build Stage)
# ============================================
FROM gradle:8-jdk21 AS build

WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY build.gradle settings.gradle ./

# Copiar el wrapper de Gradle
COPY gradlew ./
COPY gradle ./gradle

# Dar permisos de ejecución al gradlew
RUN chmod +x gradlew

# Descargar dependencias (se cachea si no cambian)
RUN gradle dependencies --no-daemon || return 0

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación y crear el JAR
RUN gradle clean build -x test --no-daemon

# ============================================
# ETAPA 2: Ejecución (Runtime Stage)
# ============================================
FROM eclipse-temurin:21-jre-alpine

# Crear un usuario no-root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Cambiar al usuario spring
USER spring:spring

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
