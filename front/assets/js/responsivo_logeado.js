document.addEventListener("DOMContentLoaded", () => {

    let persona_logueada = JSON.parse(sessionStorage.getItem("id"));
    let cerrar_sesion = document.getElementById("cerrarResponsive");

    if(persona_logueada !== null && persona_logueada !== undefined){
        const urlApi = `http://localhost:8080/api/usuarios/${persona_logueada}`;
        fetch(urlApi)
            .then(res => {
                if (!res.ok) throw new Error('Error en fetch: ' + res.status);
                return res.json();
            })
            .then(usuario => {
                let imagen = document.getElementById("imgResponsive");
                let logueado = document.getElementById("logueadoResponsive");
                let nologueado = document.getElementById("nologueadoResponsive");
                if(usuario.imagen) imagen.src = "http://localhost:8080" + usuario.imagen;
                else imagen.src = "https://cdn-icons-png.flaticon.com/512/149/149071.png";

                if (logueado) logueado.style.display = "block";
                if (nologueado) nologueado.style.display = "none";
            })
            .catch(err => {
                console.error('Error al conectar con el servidor: ' + err.message);
            });
    }else{
        document.getElementById("logueadoResponsive").setAttribute("style", "display:none");
        document.getElementById("nologueadoResponsive").setAttribute("style", "display:block");
    }

    cerrar_sesion.addEventListener("click", (ev)=>{
        ev.preventDefault();
        sessionStorage.removeItem("id");
        window.location.href = "/usuarios/iniciarsesion/index.html";
    })
});