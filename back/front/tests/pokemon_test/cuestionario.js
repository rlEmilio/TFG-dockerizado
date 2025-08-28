window.onload = function () {
  let botonEmpezar = document.getElementsByTagName("button")[0];
  let inicio = document.getElementsByClassName("inicio")[0];
  let contenedorCarrusel =
    document.getElementsByClassName("carrusel_container")[0];
  let botonAvanzar = document.getElementById("avanzar");
  const radios = document.querySelectorAll('input[type="radio"]');

  contenedorCarrusel.hidden = true;

  botonEmpezar.addEventListener("click", function () {
    inicio.hidden = true;
    contenedorCarrusel.hidden = false;
  });

  botonAvanzar.addEventListener("click", nextQuestion);

  let currentIndex = 0;
  numeroPregunta = 1;
  puntos = 0;

  //voy pasando de pregunta si al menos una opcion esta seleccionada
  //tambien voy sumando puntos segun la respuesta
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
          titulo.innerText = "Tipo fuego";
          imagen_resultado.src = "../imagenes/pokemon/moltres.jpg";
          texto.innerText =
            "El fuego late en tu corazón. Eres una persona valiente, dispuesta a ayudar a los demás. Te gusta enfrentarte a retos, aunque a veces corres riesgos innecesarios. Trata de cuidarte y ten paciencia cuando las cosas se compliquen.";
        } else if (puntos >= 60 && puntos <= 74) {
          titulo.innerText = "Tipo agua";
          imagen_resultado.src = "../imagenes/pokemon/kyogre.jpg";
          texto.innerText =
            "Eres adaptable y fluido como el agua. Sueles mantener la calma en situaciones difíciles y tienes una gran capacidad para conectar con los demás. Sin embargo, a veces te dejas llevar demasiado por las emociones.";
        } else if (puntos >= 75 && puntos <= 89) {
          titulo.innerText = "Tipo eléctrico";
          imagen_resultado.src = "../imagenes/pokemon/zapdos.jpg";
          texto.innerText =
            "Tienes una energía contagiosa y siempre estás en movimiento. Te encanta probar cosas nuevas y afrontar desafíos con entusiasmo, aunque a veces actúas sin pensar.";
        } else if (puntos >= 90 && puntos <= 104) {
          titulo.innerText = "Tipo planta";
          imagen_resultado.src = "../imagenes/pokemon/celebi.png";
          texto.innerText =
            "Eres paciente, confiable y en sintonía con la naturaleza. Prefieres la estabilidad a los cambios bruscos, lo que te hace una persona constante y leal. A veces te cuesta adaptarte a lo inesperado.";
        } else if (puntos >= 105 && puntos <= 119) {
          titulo.innerText = "Tipo hielo";
          imagen_resultado.src = "../imagenes/pokemon/articuno.png";
          texto.innerText =
            "Eres una persona serena y calculadora, pero con un lado amable que pocos conocen. Aunque no muestras tus emociones fácilmente, eres leal a quienes confían en ti.";
        } else if (puntos >= 120 && puntos <= 134) {
          titulo.innerText = "Tipo psíquico";
          imagen_resultado.src = "../imagenes/pokemon/mewtwo.jpg";
          texto.innerText =
            "Tienes una mente brillante y analítica. Te gusta pensar antes de actuar y siempre buscas comprender el porqué de las cosas. Sin embargo, a veces te exiges demasiado y necesitas relajarte.";
        } else if (puntos >= 135 && puntos <= 149) {
          titulo.innerText = "Tipo dragón";
          imagen_resultado.src = "../imagenes/pokemon/rayquaza.jpg";
          texto.innerText =
            "Eres una persona ambiciosa y te gusta superar tus límites. Posees un gran sentido del honor y la determinación para lograr lo que te propones, aunque a veces puedes ser un poco terco.";
        } else if (puntos >= 150 && puntos <= 164) {
          titulo.innerText = "Tipo fantasma";
          imagen_resultado.src = "../imagenes/pokemon/giratina.jpg";
          texto.innerText =
            "Eres misterioso y a veces difícil de descifrar. Disfrutas de la tranquilidad y la introspección, pero cuando alguien te conoce bien, descubre tu lealtad y sentido del humor único.";
        } else {
          titulo.innerText = "Tipo acero";
          imagen_resultado.src = "../imagenes/pokemon/dialga.jpg";
          texto.innerText =
            "Eres fuerte y resistente ante cualquier obstáculo. No te dejas derribar fácilmente y siempre buscas mejorar. Sin embargo, a veces puedes ser demasiado rígido con tus propias reglas.";
        }
      }
    }
  }

  function updateScore(opcion) {
    puntos += parseInt(opcion.dataset.score);
  }
};
