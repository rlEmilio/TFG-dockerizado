document.addEventListener("DOMContentLoaded", () => {
  let userId = JSON.parse(sessionStorage.getItem("id"));
  const urlApi = `http://localhost:8080/api/usuarios/${userId}`;

   //const urlApi = `http://localhost:8080/api/usuarios/20`;
  fetch(urlApi)
    .then(res => {
      if (!res.ok) throw new Error('Error en fetch: ' + res.status);
      return res.json();
    })
    .then(usuario => {
      mostrarDatosUsuario(usuario);
      mostrarActoresFavoritos(usuario.actoresDeVoz);
      mostrarClubsFavoritos(usuario.clubs);
    })
    .catch(err => {
      Swal.fire({
        title: 'Error',
        text: 'Error al conectar con el servidor: ' + err.message,
        icon: 'error',
        confirmButtonText: 'Ok'
      });
    });

  function mostrarDatosUsuario(usuario) {
    const nombreUsuario = document.getElementById('nombreUsuario');
    const correoUsuario = document.getElementById('correoUsuario');
    const imagenUsuario = document.getElementById('imagenUsuario');

    if (nombreUsuario) nombreUsuario.textContent = usuario.username;
    if (correoUsuario) correoUsuario.textContent = usuario.correo;
   
    //cambiar esta base url segun local o hosting
    const baseUrlBackend = "http://localhost:8080";

    if (imagenUsuario && usuario.imagen) {
      imagenUsuario.src = baseUrlBackend + usuario.imagen;
    }

  }

  function mostrarActoresFavoritos(actores) {
    const contenedor = document.getElementById('actoresFavoritos');
    const input = document.getElementById('buscar-actor');
    if (!contenedor) return;

    let listaCompleta = actores || [];

    function renderLista(filtrados) {
      contenedor.innerHTML = '';

      if (!filtrados || filtrados.length === 0) {
        contenedor.textContent = 'No se encontraron actores favoritos.';
        return;
      }

      // Contenedor de filas de Bootstrap
      const row = document.createElement("div");
      row.classList.add("row", "g-4");
      contenedor.appendChild(row);

      filtrados.forEach(actor => {
        const div = document.createElement("div");
        div.classList.add('col-12', 'col-sm-6', 'col-md-4');
        row.appendChild(div);

        // Tarjeta del actor
        const card = document.createElement("div");
        card.classList.add("card", "h-100", "shadow-sm", "rounded");
        div.appendChild(card);

        // Imagen del actor
        const img = document.createElement("img");
        img.src = actor.imagen || "ruta_por_defecto.jpg";
        img.alt = actor.nombre;
        img.classList.add("card-img-top", "img-fluid");
        img.style.objectFit = "cover";
        img.style.height = "350px";
        card.appendChild(img);


        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");
        card.appendChild(cardBody);


        const nombre = document.createElement("h5");
        nombre.classList.add("card-title");
        const enlace = document.createElement("a");
        enlace.href = `../informacion_actores_de_voz/index.html?id_actor=${actor.id_actor}`;
        enlace.textContent = actor.nombre;
        nombre.appendChild(enlace);
        cardBody.appendChild(nombre);


        const descripcion = document.createElement("p");
        descripcion.classList.add("card-text");
        descripcion.textContent = `Popularidad: ${actor.popularidad}`;
        cardBody.appendChild(descripcion);
      });
    }

    renderLista(listaCompleta);

    if (input) {
      input.addEventListener('input', () => {
        const texto = input.value.toLowerCase();
        const filtrados = listaCompleta.filter(actor =>
          actor.nombre.toLowerCase().includes(texto)
        );
        renderLista(filtrados);
      });
    }
  }

  function mostrarClubsFavoritos(clubs) {
    const contenedor = document.getElementById('clubsFavoritos');
    if (!contenedor) return;

    contenedor.innerHTML = '';

    if (!clubs || clubs.length === 0) {
      contenedor.textContent = "No perteneces a ningún club.";
      return;
    }

    const row = document.createElement("div");
    row.classList.add("row", "g-4");
    contenedor.appendChild(row);

    clubs.forEach(club => {
      const div = document.createElement("div");
      div.classList.add('col-12', 'col-sm-6', 'col-md-4');
      row.appendChild(div);

      const card = document.createElement("div");
      card.classList.add("card", "h-100", "shadow-sm", "rounded");
      div.appendChild(card);

      // Imagen del club
      const img = document.createElement("img");
      img.src = club.imagen || "ruta_por_defecto_club.jpg";
      img.alt = club.nombre;
      img.classList.add("card-img-top", "img-fluid");
      img.style.objectFit = "cover";
      img.style.height = "350px";
      card.appendChild(img);

      const cardBody = document.createElement("div");
      cardBody.classList.add("card-body");
      card.appendChild(cardBody);

      const nombre = document.createElement("h5");
      nombre.classList.add("card-title");
      nombre.textContent = club.nombre;
      cardBody.appendChild(nombre);

      const descripcion = document.createElement("p");
      descripcion.classList.add("card-text");
      descripcion.textContent = club.descripcion;
      cardBody.appendChild(descripcion);
    });
  }


});