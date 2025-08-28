document.addEventListener("DOMContentLoaded", ()=>{

    let divActores = document.getElementById("tarjetaActores");

    getActores();

    let actores = []




    function getActores () {
        const url = "http://localhost:8080/api/actores";

        fetch(url)
            .then((respuesta)=>{
                //Capturo la respuesta        
                if(!respuesta.ok){
                    throw new Error("Error del fetch: " + respuesta.status);
                }
                return respuesta.json();
            })
            .then((datos)=>{
                //Capturo y gestiono los datos
                for (let array of datos) { 
                    createCard(array.nombre, array.imagen, array.popularidad, array.id_actor);

                    actores.push([array.nombre, array.imagen, array.popularidad, array.id_actor]);
                }
            })
            .catch((error)=>{
                Swal.fire({
                    title: 'Error',
                    text: 'Problemas accediendo a la URL.',
                    icon: 'error',
                    confirmButtonText: 'Ok'
                });
            })
    }

    function createCard(nombre, imagen, texto, id_actor){
        //Creando el div contenedor principal
        let divPadre = document.createElement("div");
        divPadre.classList.add("col-lg-4", "col-md-6", "aos-init", "aos-animate");
        divPadre.setAttribute("data-aos", "fade-up");
        divPadre.setAttribute("data-aos-delay", "300");

        //Creando el div member
        let divMember = document.createElement("div");
        divMember.classList.add("member");

        //Creando div contenedora de imagen
        let divImagen = document.createElement("div");
        divImagen.classList.add("pic");
        let img = document.createElement("img");
        img.classList.add("img-fluid");
        img.setAttribute("src", imagen);
        img.setAttribute("alt", nombre);
        divImagen.appendChild(img);//Agrego la imagen al div

        //Creando div con la info del actor
        let infoDiv = document.createElement("div");
        infoDiv.classList.add("member-info");
        let textoNombre = document.createElement("h4");
        textoNombre.textContent = nombre;
        let descripcion = document.createElement("span");
        descripcion.textContent = texto;

        let tarjetaTexto = document.createElement("h6");
        tarjetaTexto.classList.add("card-text");
        let linkActor = document.createElement("small");
        linkActor.classList.add("text-body-secondary");
        let enlace = document.createElement("a");
        enlace.setAttribute("href", "#");
        enlace.textContent = "Leer más ->";

        enlace.addEventListener("click", (ev) => {
            ev.preventDefault();
            window.location.href = `/informacion_actores_de_voz/index.html?id_actor=${id_actor}`;
        });

        //Realizando la unión de todos los elementos
        linkActor.appendChild(enlace);
        tarjetaTexto.appendChild(linkActor);

        let idUsuario = JSON.parse(sessionStorage.getItem("id"));

        if(idUsuario){
            //Agregar a favoritos
            let p = document.createElement("p");
            p.classList.add("card-text");
            let btnFav = document.createElement("button");
            btnFav.classList.add("btn", "btn-secondary", "mt-2", "btn-sm");
            comprobarActorFavorito(btnFav, idUsuario, nombre);

            btnFav.addEventListener("click", () => {

                fetch(`http://localhost:8080/api/usuarios/agregar_actor_favorito/${idUsuario}/${id_actor}`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' }
                })
                .then(res => {
                    if (!res.ok) throw new Error("Error al insertar el actor como favorito");
                    return res.json();
                })
                .then(data => {
                  Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Se ha añadido correctamente",
                    showConfirmButton: false,
                    timer: 2000
                  });

              setTimeout(() => {
                window.location.reload();
                document.getElementById("buscarActor").value = ""; //para limpiar el buscador
              }, 2000);
                    
                    
                })
                .catch(err => {
                    Swal.fire({
                        title: 'Error',
                        text: err.message,
                        icon: 'error',
                        confirmButtonText: 'Ok'
                    });
                });
            });
            p.appendChild(btnFav);
            tarjetaTexto.appendChild(p);
        }

        infoDiv.appendChild(textoNombre);
        infoDiv.appendChild(descripcion);
        infoDiv.appendChild(tarjetaTexto);

        divMember.appendChild(divImagen);//Div 1
        divMember.appendChild(infoDiv);//Div 2

        divPadre.appendChild(divMember);

        divActores.appendChild(divPadre);//Agregando al DOM
    }

    function comprobarActorFavorito(boton, usuario, nombre){
        fetch(`http://localhost:8080/api/usuarios/${usuario}`)
        .then((res)=>{
            if (!res.ok) throw new Error("Error al pedir usuario");
            return res.json();
        })
        .then((datos)=>{
            let encontrado = false;
            let length = datos.actoresDeVoz.length;
            if(length == 0){
                boton.textContent = "Agregar a favoritos";
            }else{
                for(let i = 0; i < length && !encontrado; i++){
                    if(nombre == datos.actoresDeVoz[i].nombre){
                        boton.textContent = "Ya tienes el actor como favorito";
                        boton.disabled = true;
                        encontrado = true;
                    }else {
                        boton.textContent = "Agregar a favoritos";
                        boton.disabled = false;
                    }
                }
            }
        })
        .catch((error)=>{
            Swal.fire({
                title: 'Error',
                text: 'Problemas obteniendo el usuario',
                icon: 'error',
                confirmButtonText: 'Ok'
            });
        })
    }


    //Para el buscador de actores de voz

    let actoresCargados = [];

    let buscarActor = document.getElementById("buscarActor");

    fetch('http://localhost:8080/api/actores')
        .then(res => res.json())
        .then(actores => {

            for (const actor of actores) {
                actoresCargados.push(actor.nombre);                
            }           
        })
        .catch(err =>{
            Swal.fire({
                    title: 'Error',
                    text: 'Error al cargar actores de voz.',
                    icon: 'error',
                    confirmButtonText: 'Ok'
                });
        });

    function obtenerActores() {
        const texto = buscarActor.value.toLowerCase();
        return actoresCargados.filter(actor =>            
            actor.toLowerCase().includes(texto)
        );
    }


    buscarActor.addEventListener("input", () => {
        let array = obtenerActores();
        
        borrarCartas();

        if (buscarActor.value === "") {
            for(let i = 0; i < actores.length; i++) {   
                createCard(actores[i][0], actores[i][1], actores[i][2], actores[i][3]);
            }
        } else {
            for(let i = 0; i < actores.length; i++) {
                if (array.includes(actores[i][0])) {
                    createCard(actores[i][0], actores[i][1], actores[i][2], actores[i][3]);
                }
            }
        }
    });


    function borrarCartas () {
        let papi = document.getElementById("tarjetaActores");
        while (papi.firstChild) {
            papi.removeChild(papi.firstChild);
        }
    }
})