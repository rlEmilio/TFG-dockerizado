window.onload = async function () {
    const areaTexto = document.getElementById('newComment');
    const contadorCaracteres = document.getElementById('charCount');
    const contenedorComentarios = document.getElementById('commentFeed');
    const botonEnviar = document.getElementById('sendCommentBtn');

    let id_usuario = JSON.parse(sessionStorage.getItem("id"));
    var usuarioActual = {};
    if(id_usuario != null || id_usuario != undefined){
        const urlApi = `http://localhost:8080/api/usuarios/${id_usuario}`;
            fetch(urlApi)
            .then(res => {
                if (!res.ok) throw new Error('Error en fetch: ' + res.status);
                return res.json();
            })
            .then(usuario => {
                let img = "https://cdn-icons-png.flaticon.com/512/149/149071.png";
                if(usuario.imagen){
                    img = "http://localhost:8080" + usuario.imagen;
                }
                usuarioActual = {
                    id: id_usuario,
                    nombreUsuario: usuario.username,
                    imagen: img
                };
        
                document.getElementById("nombreUsuarioActual").textContent = usuarioActual.nombreUsuario;
                document.getElementById("avatarUsuario").src = usuarioActual.imagen;
            })
            .catch(err => {
                console.error('Error al conectar con el servidor: ' + err.message);
            });
    }

    // Cargar comentarios del backend
    try {
        const respuesta = await fetch("http://localhost:8080/api/mensajes");

        if (!respuesta.ok) {
            throw new Error("No se pudieron obtener los comentarios.");
        }

        const mensajes = await respuesta.json();

        for (const mensaje of mensajes) {
            let nombreAutor = "Usuario";
            let imagenAutor = "https://cdn-icons-png.flaticon.com/512/149/149071.png";

            try {
                const resUsuario = await fetch(`http://localhost:8080/api/usuarios/${mensaje.id_usuario}`);

                if (resUsuario.ok) {
                    const datosUsuario = await resUsuario.json();

                    nombreAutor = datosUsuario.username || nombreAutor;
                    imagenAutor = datosUsuario.imagen 
                        ? `http://localhost:8080${datosUsuario.imagen}` 
                        : imagenAutor;
                }
            } catch (error) {
                Swal.fire({
                    title: 'Error',
                    text: `Error al obtener usuario ${mensaje.id}:`, error,
                    icon: 'error',
                    confirmButtonText: 'Ok'
                });
            }

            mostrarComentario({
                autor: nombreAutor,
                contenido: mensaje.texto,
                imagen: imagenAutor
            });
        }
    } catch (error) {
        console.error("Error al cargar comentarios:", error);
        Swal.fire({
            title: 'Error',
            text: "No se pudieron cargar los comentarios.",
            icon: 'error',
            confirmButtonText: 'Ok'
        });
    }



    // Contador de caracteres
    areaTexto.addEventListener('input', () => {
        contadorCaracteres.textContent = `${areaTexto.value.length} / 280`;
    });

    // Enviar nuevo comentario
    botonEnviar.addEventListener('click', () => {
        if (JSON.parse(sessionStorage.getItem("id")) == null) {
            window.location.href = "../usuarios/iniciarsesion/index.html";
        }else{
            const texto = areaTexto.value.trim();
            if (texto.length === 0) return;

            const nuevoComentario = {
                id_usuario: usuarioActual.id,
                autor: usuarioActual.nombreUsuario,
                contenido: texto,
                imagen: usuarioActual.imagen
            };

            mostrarComentario(nuevoComentario);
            enviarComentario(nuevoComentario);

            areaTexto.value = '';
            contadorCaracteres.textContent = '0 / 280';
        }
    });

    // Mostrar comentario en el DOM
    function mostrarComentario(comentario) {
        const tarjeta = document.createElement('div');
        tarjeta.className = 'card p-3 mb-3 w-100';
        tarjeta.style.maxWidth = '800px';

        const cuerpo = document.createElement('div');
        cuerpo.className = 'd-flex align-items-start';

        const avatar = document.createElement('img');
        avatar.src = comentario.imagen;
        avatar.alt = "Avatar";
        avatar.width = 50;
        avatar.height = 50;
        avatar.className = "rounded-circle me-3";

        const contenido = document.createElement('div');
        contenido.className = 'flex-grow-1';

        const autor = document.createElement('div');
        autor.className = 'mb-2 fw-bold';
        autor.textContent = comentario.autor;

        const texto = document.createElement('p');
        texto.textContent = comentario.contenido;

        contenido.appendChild(autor);
        contenido.appendChild(texto);
        cuerpo.appendChild(avatar);
        cuerpo.appendChild(contenido);
        tarjeta.appendChild(cuerpo);

        contenedorComentarios.appendChild(tarjeta);//esta linea es la que hace que se añadan abajo en vez de arriba

    }

    //Enviar al backend
    function enviarComentario(comentario) {
        fetch('http://localhost:8080/api/mensajes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                id_usuario: comentario.id_usuario,
                texto: comentario.contenido
            })
        })
        .then(res => {
            if (!res.ok) throw new Error("Error al guardar el comentario");
            return res.json();
        })
        .then(data => {
            Swal.fire({
                text: "Comentario añadido",
                confirmButtonText: 'Ok'
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: "Error al enviar el comentario",
                icon: 'error',
                confirmButtonText: 'Ok'
            });
        });
    }
};

