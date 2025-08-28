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

        let resultado = document.getElementsByClassName("resultado")[0];
        let titulo = document.getElementById("tipo_obtenido");
        let imagen_resultado = document.getElementById("imagen_tipo");
        let texto = document.getElementById("parrafo_resultado");

        if (puntos >= 45 && puntos <= 59) {
          titulo.innerText = "Goku";
          imagen_resultado.src = "../imagenes/dragon_ball/goku.jpg";
          texto.innerText =
            "Eres valiente, amable y siempre buscas superarte. Tu espíritu de lucha es inigualable y tienes un gran corazón, incluso frente a tus enemigos.";
        } else if (puntos >= 60 && puntos <= 74) {
          titulo.innerText = "Vegeta";
          imagen_resultado.src = "../imagenes/dragon_ball/vegeta.jpg";
          texto.innerText =
            "Orgulloso y determinado, tu fuerza de voluntad es tu mayor poder. Aunque parezcas frío, en el fondo te preocupas profundamente por los que amas.";
        } else if (puntos >= 75 && puntos <= 89) {
          titulo.innerText = "Gohan";
          imagen_resultado.src = "../imagenes/dragon_ball/gohan.jpg";
          texto.innerText =
            "Inteligente y poderoso, tienes un gran potencial oculto. Te mueves por principios y siempre estás dispuesto a defender lo que es justo.";
        } else if (puntos >= 90 && puntos <= 104) {
          titulo.innerText = "Piccolo";
          imagen_resultado.src = "../imagenes/dragon_ball/piccolo.jpg";
          texto.innerText =
            "Sabio, tranquilo y estratega. Eres un mentor natural, con una gran capacidad de sacrificio por los demás. Tu madurez te distingue del resto.";
        } else if (puntos >= 105 && puntos <= 119) {
          titulo.innerText = "Krillin";
          imagen_resultado.src = "../imagenes/dragon_ball/krillin.jpg";
          texto.innerText =
            "Valiente y leal, nunca retrocedes ante el peligro a pesar de no ser el más fuerte. Tu determinación y amistad son tu mayor poder.";
        } else if (puntos >= 120 && puntos <= 134) {
          titulo.innerText = "Trunks";
          imagen_resultado.src = "../imagenes/dragon_ball/trunks.jpg";
          texto.innerText =
            "Eres decidido, serio y muy protector. Tienes un gran sentido del deber y no dudas en hacer sacrificios por un futuro mejor.";
        } else if (puntos >= 135 && puntos <= 149) {
          titulo.innerText = "Bulma";
          imagen_resultado.src = "../imagenes/dragon_ball/bulma.jpg";
          texto.innerText =
            "Inteligente, ingeniosa y siempre un paso adelante. Aunque no luchas como los demás, tu mente brillante ha salvado al mundo muchas veces.";
        } else if (puntos >= 150 && puntos <= 164) {
          titulo.innerText = "Android 18";
          imagen_resultado.src = "../imagenes/dragon_ball/android18.jpg";
          texto.innerText =
            "Fuerte, directa y segura de ti misma. Eres independiente y no temes enfrentarte a cualquiera si se trata de proteger a los tuyos.";
        } else {
          titulo.innerText = "Majin Buu";
          imagen_resultado.src = "../imagenes/dragon_ball/majinbuu.jpg";
          texto.innerText =
            "Divertido, impredecible y poderoso. Aunque pareces inocente o caótico, tienes un gran corazón cuando estás del lado del bien.";
        }
      }
    }
  }

  function updateScore(opcion) {
    puntos += parseInt(opcion.dataset.score);
  }
};
