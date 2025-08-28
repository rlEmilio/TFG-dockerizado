# Dockerfile frontend
FROM nginx:alpine

# Copiamos todos los archivos del frontend al directorio de Nginx
COPY . /usr/share/nginx/html

# Exponemos el puerto 80
EXPOSE 80

# Mantenemos Nginx en primer plano
CMD ["nginx", "-g", "daemon off;"]
