document.addEventListener("DOMContentLoaded", () => {
    let patronCorreo = /\b[\w\.-]+@[\w\.-]+\.\w{2,4}\b/i; //global y case sensitive
    let patronContrasenia = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/; //global y multilinea
    let user = false, mail = false, pass = false;

    const urlUsuarios = "http://localhost:8080/api/usuarios";
    const urlImagenes = "http://localhost:8080/api/images/upload";

    let selectedFile = null; // Imagen seleccionada (si hay)

    // Vista previa automática al seleccionar imagen
    document.getElementById("fileInput").addEventListener("change", () => {

        const fileInput = document.getElementById("fileInput");

        if (fileInput.files.length === 0) return;
        selectedFile = fileInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            const previewDiv = document.getElementById("imagePreview");
            previewDiv.innerHTML = `<img src="${e.target.result}" alt="Imagen seleccionada" style="max-width: 150px; max-height: 150px; border-radius: 5px;" />`;
        };

        reader.readAsDataURL(selectedFile);
    });
  
  

    document.getElementById("registrarse").addEventListener("click", (ev) => {
        ev.preventDefault();

        borrarErrores();

        let usuario = document.getElementById("usuario").value; //padre-usuario
        let correo = document.getElementById("correo").value; //padre - correo
        let contrasenia = document.getElementById("contrasenia").value; //padre - contrasenia
        let contrasenia2 = document.getElementById("contrasenia2").value; //padre - contrasenia2

        if (usuario != "") {
            if (usuario.length >= 3 && usuario.length <= 15) {
                user = true;
            } else {
                mostrarErrores("usuario", "El usuario tiene entre 3 y 15 caracteres.");
            }
        } else {
            mostrarErrores("usuario", "El usuario es obligatorio.");
        }

        if (correo != "") {
            if (patronCorreo.test(correo)) {
                mail = true;
            } else {
                mostrarErrores("correo", "El correo no es válido (ejemplo@gmail.com).");
            }
        } else {
            mostrarErrores("correo", "El correo es obligatorio.");
        }


        if (contrasenia != "" || contrasenia2 != "") {
            if (contrasenia != "" && contrasenia2 == "") {
                mostrarErrores("contrasenia2", "Es necesario repetir la contraseña.");
            } else if (contrasenia != contrasenia2) {
                mostrarErrores("contrasenia", "Las contraseñas son distintas.");
            } else if ((contrasenia == contrasenia2) && (patronContrasenia.test(contrasenia)) && (patronContrasenia.test(contrasenia2))) {
                pass = true;
            } else {
                mostrarErrores("contrasenia", "Como mínimo 8 caracteres, una letra mayuscula, una minúscula y un número, y puede tener un caracter especial.");
            }
        } else {
            mostrarErrores("contrasenia", "La contraseña es obligatoria.");
            mostrarErrores("contrasenia2", "Es necesario repetir la contraseña.");
        }

      let respuesta = grecaptcha.getResponse();
      let terminos = document.getElementById("terminos");
      
      if (!terminos.checked) {
        Swal.fire({
            title: 'Error',
            text: 'Es necesario aceptar los términos y condiciones.',
            icon: 'error',
            confirmButtonText: 'Ok'
          });
      } else if (respuesta.length == 0) {
        Swal.fire({
            title: 'Error',
            text: 'Es necesario aceptar el captcha.',
            icon: 'error',
            confirmButtonText: 'Ok'
          });
      }
        else if (user && mail && pass &&  respuesta.length != 0) {
            fetch(urlUsuarios)
                .then(response => {
                    if (!response.ok) throw new Error("Error al obtener los usuarios");
                    return response.json();
                })
                .then(usuarios => {
                    const existe = usuarios.some(u => u.usuario === usuario);

                    if (existe) {
                        mostrarErrores("usuario", "Este usuario ya está registrado. Inicia sesión.");
                        return;
                    }

                    return fetch(urlUsuarios, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({
                            username: usuario,
                            correo: correo,
                            contrasenia: contrasenia
                        })
                    });
                })
                .then(res => {
                    if (!res) return;
                    if (!res.ok) throw new Error("Error al registrar el usuario");
                    return res.json();
                })
                .then(async data => {
                    if (!data) return;

                    const userId = data.id_usuario;

                    // Subir imagen si fue seleccionada
                    if (selectedFile) {
                        const formData = new FormData();
                        formData.append("file", selectedFile);

                        const imageUploadRes = await fetch(`${urlImagenes}/${userId}`, {
                            method: "POST",
                            body: formData
                        });
                        if (!imageUploadRes.ok) {
                            Swal.fire({
                                title: 'Error',
                                text: 'Usuario creado, pero ocurrió un error al subir la imagen.',
                                icon: 'error',
                                confirmButtonText: 'Ok'
                            });
                            return;

                        }
                    }
           			Swal.fire({
                      position: "center",
                      icon: "success",
                      title: "Usuario registrado correctamente",
                      showConfirmButton: false,
                      timer: 2000
                    });
                    setTimeout(() => {
                        window.location.href = "../iniciarsesion/index.html";
                    }, 2000);
                })
                .catch(error => {
                    Swal.fire({
                        title: 'Error',
                        text: 'Error en el registro.',
                        icon: 'error',
                        confirmButtonText: 'Ok'
                    });
                });
        }
    });



    function mostrarErrores (padre, mensaje) {
        let caja = "";        

        switch (padre) {
            case "usuario":
                caja = document.getElementById("errorusuario");
                caja.innerText = mensaje;
                break;
            case "correo":
                caja = document.getElementById("errorcorreo");
                caja.innerText = mensaje;
                break;
            case "contrasenia":
                caja = document.getElementById("errorcontrasenia");
                caja.innerText = mensaje;
                break;
            case "contrasenia2":
                caja = document.getElementById("errorcontrasenia2");
                caja.innerText = mensaje;
                break;
        }
    }

    function borrarErrores () {
        let papi = document.querySelectorAll(".login-form span");               

        papi.forEach((elemento) => {
            elemento.innerText = "";
        })
    }

});