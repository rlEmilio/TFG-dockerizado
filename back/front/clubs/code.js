window.onload = function () {
    let clubesCargados = [];
    let paginaActual = 1;
    const clubsPorPagina = 5;

    const tbody = document.getElementById('clubs-body');
    const inputBusqueda = document.getElementById('busquedaClub');
    const btnPrev = document.getElementById('prevPage');
    const btnNext = document.getElementById('nextPage');

    fetch('http://localhost:8080/api/clubs')
        .then(res => res.json())
        .then(clubs => {
            clubesCargados = clubs;
            renderizarPagina();
        })
        .catch(err => {
            Swal.fire({
                title: 'Error',
                text: 'Error al cargar clubs.',
                icon: 'error',
                confirmButtonText: 'Ok'
            });
        });

    inputBusqueda.addEventListener('input', () => {
        paginaActual = 1;
        renderizarPagina();
    });

    // Manejo de eventos para los botones de paginación
    btnPrev.addEventListener('click', () => {
        if (paginaActual > 1) {
            paginaActual--;
            renderizarPagina();
        }
    });

    btnNext.addEventListener('click', () => {
        const filtrados = obtenerClubesFiltrados();
        const totalPaginas = Math.ceil(filtrados.length / clubsPorPagina);
        if (paginaActual < totalPaginas) {
            paginaActual++;
            renderizarPagina();
        }
    });
    // Función para obtener los clubes filtrados según la búsqueda
    function obtenerClubesFiltrados() {
        const texto = inputBusqueda.value.toLowerCase();
        return clubesCargados.filter(club =>
            club.nombre.toLowerCase().includes(texto)
        );
    }
    // Función para renderizar la tabla de clubes
    // y manejar la paginación
    function renderizarPagina() {
        const clubsFiltrados = obtenerClubesFiltrados();
        const totalPaginas = Math.ceil(clubsFiltrados.length / clubsPorPagina);
        const inicio = (paginaActual - 1) * clubsPorPagina;
        const fin = inicio + clubsPorPagina;
        const clubsPagina = clubsFiltrados.slice(inicio, fin);

        tbody.innerHTML = '';
        clubsPagina.forEach(club => {
            const fila = crearFilaClub(club);
            tbody.appendChild(fila);
        });

        btnPrev.disabled = paginaActual === 1;
        btnNext.disabled = paginaActual === totalPaginas || totalPaginas === 0;
    }

    // Función para crear una fila de club
    function crearFilaClub(club) {
        const tr = document.createElement('tr');

        // Columna info club (imagen + texto)
        const tdInfo = document.createElement('td');
        tdInfo.className = 'text-start';

        const divContenedor = document.createElement('div');
        divContenedor.className = 'd-flex flex-column flex-sm-row align-items-start gap-2';

        const img = document.createElement('img');
        img.src = club.imagen;
        img.alt = club.nombre;
        img.className = 'rounded';
        img.width = 60;
        img.height = 60;

        const divTexto = document.createElement('div');
        const nombre = document.createElement('strong');
        nombre.textContent = club.nombre;

        const descripcion = document.createElement('small');
        descripcion.textContent = club.descripcion || '';
        descripcion.className = 'd-block';

        divTexto.appendChild(nombre);
        divTexto.appendChild(document.createElement('br'));
        divTexto.appendChild(descripcion);

        divContenedor.appendChild(img);
        divContenedor.appendChild(divTexto);
        tdInfo.appendChild(divContenedor);

        // Columna miembros
        const tdMiembros = document.createElement('td');
        tdMiembros.textContent = club.miembros;

        // Columna botón
        const tdBoton = document.createElement('td');

        const idUsuario = JSON.parse(sessionStorage.getItem("id"));

        //Función para crear el botón de Unirse
        function crearBotonUnirse() {
            const boton = document.createElement('button');

            boton.textContent = 'Unirse';
            boton.className = 'btn btn-editar btn-sm';

            boton.addEventListener('click', () => {
                if (!idUsuario) {
                    window.location.href = "../usuarios/iniciarsesion/index.html";
                    return;
                }

                fetch(`http://localhost:8080/api/clubs/agregar_usuario/${club.id_club}/${idUsuario}`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' }
                })
                    .then(res => {
                        if (!res.ok) throw new Error("Error al insertar");
                        return res.json();
                    })
                    .then(data => {
                        Swal.fire({
                            text: data.Mensaje,
                            icon: 'success',
                            confirmButtonText: 'Ok'
                        });
                        boton.remove();
                        tdBoton.textContent = "Ya eres miembro";
                        tdBoton.classList.add("text-white");

                        // Actualizar número de miembros
                        tdMiembros.textContent = (parseInt(tdMiembros.textContent) + 1).toString();
                    })
                    .catch(err => {
                        Swal.fire({
                            title: 'Error',
                            text: '"No se pudo unir al club.',
                            icon: 'error',
                            confirmButtonText: 'Ok'
                        });
                    });
            });

            return boton;
        }

        //Verificar si usuario ya es miembro
        if (!idUsuario) tdBoton.appendChild(crearBotonUnirse("Unirse"));
        else {
            fetch(`http://localhost:8080/api/usuarios/${idUsuario}`)
                .then(res => {
                    if (!res.ok) throw new Error("Error al obtener usuario");
                    return res.json();
                })
                .then(usuario => {
                    const yaEsMiembro = usuario.clubs.some(c => c.id_club === club.id_club);
                    if (!yaEsMiembro) {
                        tdBoton.appendChild(crearBotonUnirse());
                    } else {
                        tdBoton.textContent = 'Ya eres miembro';
                        tdBoton.className = "text-white";
                    }
                })
                .catch(err => {
                    tdBoton.textContent = 'Error al verificar';
                    Swal.fire({
                        title: 'Error',
                        text: 'Error al obtener usuario.',
                        icon: 'error',
                        confirmButtonText: 'Ok'
                    });
                });
        }

        tr.appendChild(tdInfo);
        tr.appendChild(tdMiembros);
        tr.appendChild(tdBoton);

        return tr;
    }
};