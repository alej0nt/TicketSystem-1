# ============================================
# ETAPA 1: Construcción (Build Stage)
# ============================================
FROM node:20-alpine AS build

WORKDIR /app

# Copiar archivos de configuración de npm
COPY package*.json ./

# Instalar todas las dependencias del proyecto
RUN npm install

# Copiar TODO el código fuente al contenedor
COPY . .

# Ejecutar el build de producción de Angular
RUN npm run build

# ============================================
# ETAPA 2: Servidor Web (Production Stage)
# ============================================
FROM nginx:alpine

# Crear directorio para logs
RUN mkdir -p /var/log/nginx

# Copiar los archivos compilados al servidor Nginx
COPY --from=build /app/dist/frontend /usr/share/nginx/html

# Copiar configuración personalizada de Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponer el puerto 80
EXPOSE 80

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]
