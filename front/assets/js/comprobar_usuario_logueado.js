document.addEventListener("DOMContentLoaded", () => {
    let persona_logueada = JSON.parse(sessionStorage.getItem("id"));
    let cerrar_sesion = document.getElementById("cerrar");

    if (persona_logueada !== null && persona_logueada !== undefined) {
        const urlApi = `http://localhost:8080/api/usuarios/${persona_logueada}`;
        fetch(urlApi)
            .then(res => {
                if (!res.ok) throw new Error('Error en fetch: ' + res.status);
                return res.json();
            })
            .then(usuario => {
                let imagen = document.getElementById("imagen_usuario");
                let mainCarousel = document.getElementById("mainCarousel");
                let logueado = document.getElementById("logueado");
                let nologueado = document.getElementById("nologueado");
                
                if(usuario.imagen) imagen.src = "http://localhost:8080" + usuario.imagen;
                else imagen.src = "https://cdn-icons-png.flaticon.com/512/149/149071.png";

                if (mainCarousel) {
                    if (usuario.premium) mainCarousel.style.display = "none";
                    else mainCarousel.style.display = "block";
                }
          		if (logueado) logueado.style.setProperty("display", "block", "important");
				if (nologueado) nologueado.style.setProperty("display", "none", "important");
            })
            .catch(err => {
                console.error('Error al conectar con el servidor: ' + err.message);
            });
    } else {
      	console.log("no logueado");
        const logueado = document.getElementById("logueado");
        const nologueado = document.getElementById("nologueado");

        if (logueado) logueado.style.display = "none";
        if (nologueado) nologueado.style.display = "block";
    }

    if (cerrar_sesion) {
        cerrar_sesion.addEventListener("click", (ev) => {
            ev.preventDefault();
            sessionStorage.removeItem("id");
            window.location.href = "../usuarios/iniciarsesion/index.html";
        });
    }
});
