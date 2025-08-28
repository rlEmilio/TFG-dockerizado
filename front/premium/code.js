const btnPremium = document.getElementById("btn-premium");

btnPremium.addEventListener("click", function() {
    if (JSON.parse(sessionStorage.getItem("id")) != null) {
        window.location.href = "../pago_premium/index.html";
    }else
        window.location.href = "../usuarios/iniciarsesion/index.html";
})