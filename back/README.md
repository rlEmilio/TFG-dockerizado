He creado nuevas Entidades, repositorios y servicios para las tablas clubs y comunidad, asi como adaptado la tabla mensajes con las nuevas relaciones.
Es necesaria la entidad Comunidad ya que nos sirve para mostrar a que usuario pertenece un mensaje escrito dentro de una comunidad.
Tambien he creado una Nueva clase ComunidadId para formar una clave compuesta con las claves id_usuario e id_mensaje, ya 
que JPA obliga a usar una clave primaria. 
Probaré las solicitudes HTTP con Postman para ver si el back está funcionando, o al menos alguna parte.
