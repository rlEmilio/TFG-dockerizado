document.addEventListener("DOMContentLoaded", () => {
  
    document.getElementById("iniciarsesion").addEventListener("click", function (e) {
     	let respuesta = grecaptcha.getResponse();
        
        e.preventDefault();
      	if (respuesta.length != 0) {
       
        const usuario = document.getElementById("usuario").value;
        const contrasenia = document.getElementById("contrasenia").value;


        const cabecera = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username : usuario,
                contrasenia : contrasenia
            })
        }
localhost:8080
        fetch("http://localhost:8080/login", cabecera)
            .then(res => {
                if (!res.ok) throw new Error("Error al obtener usuarios");
                return res.json();
            })
            .then(data => {
          
          	//if (respuesta.length != 0) {
              
                sessionStorage.setItem("id", JSON.stringify(data["id"]));

                window.location.href = "../../index.html";
            //} else {
              
            //}
                        
            })
            .catch(err => {
                Swal.fire({
                    title: 'Error',
                    text: 'Error al iniciar sesión: usuario o contraseña incorrectos.',
                    icon: 'error',
                    confirmButtonText: 'Ok'
                });
            });
      
    	} else {
          Swal.fire({
            title: 'Error',
            text: 'Es necesario aceptar el captcha.',
            icon: 'error',
            confirmButtonText: 'Ok'
          });
        }
    });



});

