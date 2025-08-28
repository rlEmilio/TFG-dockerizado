window.onload = function () {
  let botonEmpezar = document.getElementsByTagName("button")[0];
  let inicio = document.getElementsByClassName("inicio")[0];
  let contenedorCarrusel = document.getElementsByClassName("carrusel_container")[0];
  let botonAvanzar = document.getElementById("avanzar");
  const radios = document.querySelectorAll('input[type="radio"]');

  contenedorCarrusel.hidden = true;

  botonEmpezar.addEventListener("click", function () {
    inicio.hidden = true;
    contenedorCarrusel.hidden = false;
  });

  botonAvanzar.addEventListener("click", nextQuestion);

  let currentIndex = 0;
  let numeroPregunta = 1;
  let puntos = 0;

  function nextQuestion() {
     let opciones = document.getElementsByName("question" + numeroPregunta);
    opcionMarcada = false;
    opciones.forEach((opcion) => {
      if (opcion.checked) {
        opcionMarcada = true;
        numeroPregunta++;
        updateScore(opcion);
      }
    });

    if (opcionMarcada) {
      const carrusel = document.getElementsByClassName("carrusel")[0];
      currentIndex++;
      if (currentIndex < document.querySelectorAll(".question").length) {
        carrusel.style.transform = `translateX(-${currentIndex * 100}%)`;
      }
      if (currentIndex == document.querySelectorAll(".question").length - 1) {
        carrusel.style.transform = `translateX(-${currentIndex * 100}%)`;
        botonAvanzar.innerText = "Enviar";
      } else if (
        currentIndex >
        document.querySelectorAll(".question").length - 1
      ) {
        carrusel.style.transform = `translateX(-${currentIndex * 100}%)`;
        botonAvanzar.hidden = true;

        //muestro resultados
        let resultado = document.getElementsByClassName("resultado")[0];
        let titulo = document.getElementById("tipo_obtenido");
        let imagen_resultado = document.getElementById("imagen_tipo");
        let texto = document.getElementById("parrafo_resultado");

        if (puntos >= 45 && puntos <= 59) {
          titulo.innerText = "Luffy";
          imagen_resultado.src = "../imagenes/one_piece/luffy.jpg";
          texto.innerText =
            "Eres valiente, siempre lleno de energía y con un espíritu libre que no teme enfrentarse a cualquier desafío. Tu determinación y lealtad a tus amigos te definen.";
        } else if (puntos >= 60 && puntos <= 74) {
          titulo.innerText = "Zoro";
          imagen_resultado.src = "../imagenes/one_piece/zoro.jpg";
          texto.innerText =
            "Fuerte, disciplinado y con un gran sentido del honor. Siempre dispuesto a proteger a los que amas y a nunca rendirte, aunque el camino sea difícil.";
        } else if (puntos >= 75 && puntos <= 89) {
          titulo.innerText = "Nami";
          imagen_resultado.src = "../imagenes/one_piece/nami.jpeg";
          texto.innerText =
            "Inteligente y estratégica, tienes un talento natural para la navegación y el liderazgo. Sabes cómo sacar ventaja de cada situación y proteger a tu equipo.";
        } else if (puntos >= 90 && puntos <= 104) {
          titulo.innerText = "Sanji";
          imagen_resultado.src = "../imagenes/one_piece/sanji.jpg";
          texto.innerText =
            "Apasionado y con un gran sentido del estilo, valoras la amistad y la justicia. Eres generoso y no dudas en ayudar a quien lo necesite, con un toque romántico.";
        } else if (puntos >= 105 && puntos <= 119) {
          titulo.innerText = "Sogeking";
          imagen_resultado.src = "../imagenes/one_piece/usopp.jpg";
          texto.innerText =
            "Creativo y con un gran corazón, a veces dudas de ti mismo pero siempre encuentras la manera de sorprender a todos. Tu ingenio es tu mayor arma.";
        } else if (puntos >= 120 && puntos <= 134) {
          titulo.innerText = "Chopper";
          imagen_resultado.src = "../imagenes/one_piece/chopper.jpg";
          texto.innerText =
            "Tierno y compasivo, eres un sanador nato. A pesar de tu timidez, muestras una valentía impresionante cuando se trata de proteger a los demás.";
        } else if (puntos >= 135 && puntos <= 149) {
          titulo.innerText = "Robin";
          imagen_resultado.src = "../imagenes/one_piece/robin.jpg";
          texto.innerText =
            "Inteligente y misterioso, tienes un conocimiento profundo del mundo. Prefieres pensar antes de actuar, pero tu lealtad y coraje salen a la luz cuando es necesario.";
        } else if (puntos >= 150 && puntos <= 164) {
          titulo.innerText = "Franky";
          imagen_resultado.src = "../imagenes/one_piece/franky.jpg";
          texto.innerText =
            "Eres extrovertido, fuerte y con un gran talento para la creación y la innovación. Siempre con energía para apoyar a tus amigos y luchar por tus ideales.";
        } else {
          titulo.innerText = "Brook";
          imagen_resultado.src = "../imagenes/one_piece/brook.jpg";
          texto.innerText =
            "Con un gran sentido del humor y espíritu libre, aportas alegría y música a donde vayas. A pesar de las adversidades, mantienes la esperanza y la fortaleza.";
        }
      }
    }
  }

  function updateScore(opcion) {
    puntos += parseInt(opcion.dataset.score);
  }
};
