window.onload = () => {
  const paypalContainer = document.getElementById("paypal-button-container");
  const mensajePremium = document.getElementById("mensaje-premium");

  let userId = JSON.parse(sessionStorage.getItem("id"));
  if (!userId) {
    Swal.fire("No se ha identificado el usuario.");
    return;
  }

  // Verificar si el usuario ya es premium
  fetch(`http://localhost:8080/api/usuarios/${userId}`)
    .then(res => {
      if (!res.ok) throw new Error("Error al obtener datos del usuario");
      return res.json();
    })
    .then(user => {
      if (user.premium) {
        mensajePremium.classList.remove("d-none");
        paypalContainer.innerHTML = ""; // limpio contenedor
      } else {
        renderizarBotonPaypal();
      }
    })
    .catch(err => console.error("Error:", err));

  function renderizarBotonPaypal() {
    paypalContainer.innerHTML = "";  // limpio el contenedor antes de renderizar

    paypal.Buttons({
      createOrder: (data, actions) => {
        return fetch('http://localhost:8080/api/paypal/create-order', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({})
        })
          .then(res => {
            if (!res.ok) throw new Error("Error al crear la orden");
            return res.json();
          })
          .then(data => data.orderId);
      },

      onApprove: (data, actions) => {
        return fetch('http://localhost:8080/api/paypal/capture-order', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ orderId: data.orderID, userId: userId })
        })
          .then(res => {
            if (!res.ok) throw new Error("Error al capturar el pago");
            return res.json();
          })
          .then(data => {
            Swal.fire({
              text: "Pago realizado:", data,
              confirmButtonText: 'Ok'
            });
            console.log();
            mensajePremium.classList.remove("d-none");
            paypalContainer.innerHTML = "";
          })
          .catch(err => {
            Swal.fire({
              title: 'Error',
              text: "Ocurrió un error al procesar el pago.",
              icon: 'error',
              confirmButtonText: 'Ok'
            });
          });
      },

      onError: (err) => {
        Swal.fire({
          title: 'Error',
          text: "Error al procesar el pago. Intenta de nuevo.",
          icon: 'error',
          confirmButtonText: 'Ok'
        });
      }
    }).render('#paypal-button-container');
  }
};
