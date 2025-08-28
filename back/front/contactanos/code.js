
window.onload = function () {

    const form = document.getElementById("contact-form");
  	let botoncillo = document.getElementById("enviar");

    botoncillo.addEventListener("click", async function (e) {
        e.preventDefault();
        limpiarErrores();
      let terminos = document.getElementById("terminos");
      

        const captchaResponse = grecaptcha.getResponse();
      if (!terminos.checked) {
        Swal.fire("Es necesario aceptar los términos y condiciones.");
        return;
      }
      
      if (captchaResponse.length === 0) {
        Swal.fire("Por favor, verifica que no eres un robot.");
        return;
      }

        // Obtener el ID del usuario desde sessionStorage desde el login
        //const userId = sessionStorage.getItem('user_id');
        const userId = JSON.parse(sessionStorage.getItem("id"));

        const nombre = document.getElementById("nombre").value.trim();
        const email = document.getElementById("email").value.trim();
        const asunto = document.getElementById("asunto").value.trim();
        const mensaje = document.getElementById("mensaje").value.trim();
      	
      

        const patronNombre = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/;
        const patronEmail = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;

        let valido = true;

        // Validaciones
        if (!validarCampo(document.getElementById("nombre"), patronNombre,
            "El nombre no puede estar vacío.",
            "Debe tener al menos 3 letras sin números ni símbolos.")) {
            valido = false;
        }

        if (!validarCampo(document.getElementById("email"), patronEmail,
            "El correo electrónico no puede estar vacío.",
            "Formato de correo no válido.")) {
            valido = false;
        }

        if (!validarCampo(document.getElementById("asunto"), null,
            "El asunto no puede estar vacío.",
            "")) {
            valido = false;
        }

        if (!validarCampo(document.getElementById("mensaje"), null,
            "El mensaje no puede estar vacío.",
            "")) {
            valido = false;
        }

        if (!valido) return;

        try {

            const datosFormulario = {
                subject: asunto,
                body: mensaje,
                userEmail: email
            };

            console.log("Datos a enviar:", datosFormulario);

            // Enviar formulario
            const resEnvio = await fetch("http://localhost:8080/api/contacto", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(datosFormulario),
                redirect: "follow"
            });

            if (!resEnvio.ok) throw new Error("Error al enviar el formulario.");
              Swal.fire({
              position: "center",
              icon: "success",
              title: "Mensaje enviado correctamente. ¡Gracias por contactarnos!",
              showConfirmButton: false,
              timer: 2000
            });
            setTimeout(() => {
              form.reset();
              window.location.href = "../index.html";
            }, 2000);
        } catch (error) {
            console.error("Error en el proceso de envío del formulario:", error);
            Swal.fire("Hubo un problema: " + error.message);
        }

    });

    // Funciones de validación y error
    function mostrarErrorCampo(campo, mensaje) {
        const divError = document.createElement("div");
        divError.className = "error-msg";
        divError.textContent = mensaje;
        campo.parentElement.appendChild(divError);
    }

    // Limpia todos los errores
    // de validación del formulario
    function limpiarErrores() {
        document.querySelectorAll(".error-msg").forEach(e => e.remove());
    }

    // Limpia el error de un campo específico
    // y lo elimina del DOM
    function limpiarErrorCampo(campo) {
        const error = campo.parentElement.querySelector(".error-msg");
        if (error) error.remove();
    }

    // Valida un campo específico
    // y muestra un mensaje de error
    function validarCampo(campo, patron, mensajeVacio, mensajeInvalido) {
        const valor = campo.value.trim();
        limpiarErrorCampo(campo);

        if (valor === "") {
            mostrarErrorCampo(campo, mensajeVacio);
            return false;
        }

        if (patron && !patron.test(valor)) {
            mostrarErrorCampo(campo, mensajeInvalido);
            return false;
        }

        return true;
    }

};
