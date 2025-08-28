

¿QUE ES ESTA APLICACIÓN?
------------------------

Esta aplicación surge como proyecto final del ciclo superior de Desarrollo Web.
Se trata de una página tipo blog enfocada principalmente en conocer y compartir información sobre actores de voz en el mundo del anime, con posibilidad de ampliarse a actores de doblaje en general.

El objetivo del proyecto es dar visibilidad y reconocimiento a esta profesión, combinando nuestro entusiasmo por el sector con nuestra afición por el mundo del anime.

Entre sus funcionalidades destacan:

- Listados de actores y comunidades.

- Foro y sección de noticias.

- Sistema de comentarios.

- Apartado premium con pagos gestionados mediante PayPal.

- Tienda de productos que enlaza a Amazon.

- Cuestionarios interactivos tipo “Adivina qué personaje eres”.

- Sistema completo de login y registro.
  

COMO PROBAR
-----------
Es necesario tener instalado Docker: https://www.docker.com/get-started/

1. Clonar o descargar este repositorio.

2. Abrir una terminal y navegar hasta la carpeta raíz del proyecto.

3. Levantar los contenedores con:
docker-compose up --build

4. Para eliminar contenedores y volúmenes usar:
docker-compose down -v

5. Una vez lo contenedores estén levantados, acceder a la web a través de http://localhost:8081/


 
TECNOLOGÍAS USADAS
------------------
Backend:

- Spring Boot

- JPA para la conexión con la base de datos

- Spring Security para cifrado y gestión de usuarios

Frontend:

- JavaScript

- HTML5 y CSS

- Bootstrap

Base de datos:

- MySQL


COLABORADORES
-------------
- Luis-GonzalesC 
- PurpleCodee
- egpcommits
- aab0030 
- rlEmilio
