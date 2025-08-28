window.onload = function () {
  const form = document.querySelector("form");
  const id_usuario = JSON.parse(sessionStorage.getItem("id"));
  let boton = document.getElementById("actualizarCambios");
  // Base URL dinámica
  const baseUrlBackend = "http://localhost:8080";

  // Precargar datos del usuario, incluyendo imagen de perfil
  fetch(`${baseUrlBackend}/api/usuarios/${id_usuario}`)
    .then((res) => {
      if (!res.ok) throw new Error("Error al obtener datos del usuario");
      return res.json();
    })
    .then((usuario) => {
      document.getElementById("nombre").value = usuario.nombre;
      document.getElementById("genero").value = usuario.genero;

      const imagen = document.getElementById("userFoto");
      if (imagen && usuario.imagen) {
        imagen.src = baseUrlBackend + usuario.imagen;
      } else {
        imagen.src = "https://cdn-icons-png.flaticon.com/512/149/149071.png";
      }
    })
    .catch((err) => {
      console.error("Error al cargar los datos del usuario:", err);
    });

    boton.addEventListener("click", function (e) {
    e.preventDefault();
    limpiarErrores();
    

    const nombre = document.getElementById("nombre").value.trim();
    const genero = document.getElementById("genero").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("newPassword").value;
    const imagen = document.getElementById("imagen");
    const archivo = imagen.files[0];
    const photo = document.getElementById("userFoto").src;

    // Regex
    const patronPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;

    let valido = true;

    // Validaciones (nombre, género, contraseña, imagen)
      if (nombre.legnth >= 3 && nombre.length <= 15) {
        valido = false;
      } else {
        mostrarErrorCampo(document.getElementById("nombre"), "El nombre debe tener al menos 3 letras sin números ni símbolos.");
      }

    if (
      !validarCampo(
        document.getElementById("genero"),
        null,
        "El campo género no puede estar vacío.",
        ""
      )
    ) {
      valido = false;
    }

    if (
      !validarCampo(
        document.getElementById("password"),
        patronPassword,
        "El campo contraseña no puede estar vacío.",
        "Debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un símbolo."
      )
    ) {
      valido = false;
    }

    if (
      !validarCampo(
        document.getElementById("newPassword"),
        null,
        "El campo confirmar contraseña no puede estar vacío.",
        ""
      )
    ) {
      valido = false;
    } else if (password !== confirmPassword) {
      mostrarErrorCampo(
        document.getElementById("newPassword"),
        "Las contraseñas no coinciden."
      );
      valido = false;
    }

    if (!archivo) {
      mostrarErrorCampo(
        document.getElementById("imagen"),
        "Debes seleccionar una imagen de perfil."
      );
      valido = false;
    } else {
      const tiposValidos = [
        "image/jpeg",
        "image/png",
        "image/jpg",
        "image/webp",
      ];
      if (!tiposValidos.includes(archivo.type)) {
        mostrarErrorCampo(
          document.getElementById("imagen"),
          "El archivo debe ser una imagen válida (.jpg, .jpeg, .png o .webp)."
        );
        valido = false;
      }

      const maxTamMB = 5;
      if (archivo.size > maxTamMB * 1024 * 1024) {
        mostrarErrorCampo(
          document.getElementById("imagen"),
          "La imagen no puede superar los 5 MB."
        );
        valido = false;
      }
    }

    if (!valido) return;

    const formData = new FormData();
    formData.append("username", nombre);
    formData.append("contrasenia", password);
    formData.append("genero", genero);
    formData.append("imagen", archivo);

    const urlApi = `${baseUrlBackend}/api/usuarios/${id_usuario}`;

    fetch(urlApi, {
      method: "PUT",
      body: formData,
    })
      .then((res) => {
        if (!res.ok) throw new Error("Error en fetch: " + res.status);
        return res.json();
      })
      .then((datos) => {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Perfil actualizado correctamente",
          showConfirmButton: false,
          timer: 2000
        });
        setTimeout(() => {
          window.location.href = "../perfil_usuario/index.html";
        }, 2000);
      })
      .catch((err) => {
        Swal.fire("Error al conectar con el servidor: " + err.message);
      });
  });

  // Mostrar vista previa de la nueva imagen seleccionada
  document.getElementById("imagen").addEventListener("change", function () {
    const archivo = this.files[0];
    const vistaPrevia = document.getElementById("userFoto");

    if (archivo) {
      const tiposValidos = [
        "image/jpeg",
        "image/png",
        "image/jpg",
        "image/webp",
      ];
      if (tiposValidos.includes(archivo.type)) {
        const urlTemporal = URL.createObjectURL(archivo);
        vistaPrevia.src = urlTemporal;
      } else {
        // Si el tipo de archivo no es válido, no cambiamos la imagen actual
        Swal.fire("Selecciona una imagen válida (.jpg, .jpeg, .png, .webp).");
      }
    }
  });

  // Funciones auxiliares
  function mostrarErrorCampo(campo, mensaje) {
    const divError = document.createElement("div");
    divError.className = "error-msg";
    divError.textContent = mensaje;
    campo.parentElement.appendChild(divError);
  }

  function limpiarErrores() {
    document.querySelectorAll(".error-msg").forEach((e) => e.remove());
  }

  function limpiarErrorCampo(campo) {
    const error = campo.parentElement.querySelector(".error-msg");
    if (error) error.remove();
  }

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