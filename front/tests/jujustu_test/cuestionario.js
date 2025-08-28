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
            titulo.innerText = "Toge Inumaki";
            imagen_resultado.src = "../imagenes/jujutsu/toge_inumaki.jpg";
            texto.innerText =
              "Eres alguien reservado pero muy poderoso. A pesar de que hablas poco, tus acciones hablan por ti. Tu lealtad y valentía inspiran a otros.";
          } else if (puntos >= 60 && puntos <= 74) {
            titulo.innerText = "Maki Zenin";
            imagen_resultado.src = "../imagenes/jujutsu/maki_zenin.jpeg";
            texto.innerText =
              "Fuerte, decidida y sin miedo a desafiar tradiciones. A pesar de las adversidades, nunca te rindes y estás dispuesto/a a demostrar tu valía con hechos.";
          } else if (puntos >= 75 && puntos <= 89) {
            titulo.innerText = "Kento Nanami";
            imagen_resultado.src = "../imagenes/jujutsu/nanami.jpg";
            texto.innerText =
              "Responsable, lógico y determinado. Prefieres la eficacia sobre la emoción, pero en el fondo tienes un gran sentido del deber y empatía.";
          } else if (puntos >= 90 && puntos <= 104) {
            titulo.innerText = "Megumi Fushiguro";
            imagen_resultado.src = "../imagenes/jujutsu/megumi_fushiguro.jpg";
            texto.innerText =
              "Eres serio, reflexivo y siempre buscas proteger a los demás. Aunque pareces distante, te guían fuertes convicciones morales.";
          } else if (puntos >= 105 && puntos <= 119) {
            titulo.innerText = "Nobara Kugisaki";
            imagen_resultado.src = "../imagenes/jujutsu/nobara_kugisaki.jpeg";
            texto.innerText =
              "Valiente, decidida y con gran sentido del orgullo. No temes mostrarte como eres y te enfrentas a todo con actitud feroz y confiada.";
          } else if (puntos >= 120 && puntos <= 134) {
            titulo.innerText = "Yuji Itadori";
            imagen_resultado.src = "../imagenes/jujutsu/yuji_itadori.jpg";
            texto.innerText =
              "Amable, valiente y con un gran sentido de la justicia. Harías lo que fuera por proteger a los demás, incluso si eso implica sacrificarte.";
          } else if (puntos >= 135 && puntos <= 149) {
            titulo.innerText = "Satoru Gojo";
            imagen_resultado.src = "../imagenes/jujutsu/goko.jpg";
            texto.innerText =
              "Carismático, confiado y extremadamente poderoso. Aunque sueles bromear, tienes un fuerte compromiso con proteger a quienes amas.";
          } else if (puntos >= 150 && puntos <= 164) {
            titulo.innerText = "Sukuna";
            imagen_resultado.src = "../imagenes/jujutsu/sukuna.jpg";
            texto.innerText =
              "Intenso, temido y con una presencia imponente. Eres un espíritu libre que odia que lo controlen, guiado por tus propios intereses y poder.";
          } else {
            titulo.innerText = "Aoi Todo";
            imagen_resultado.src = "../imagenes/jujutsu/todo.jpg";
            texto.innerText =
              "Intenso, apasionado y con un gusto único en amigos. Tu energía arrasa con todo, y eres capaz de motivar incluso a los más débiles a luchar con el corazón.";
          }
        }
      }
    }
  
    function updateScore(opcion) {
      puntos += parseInt(opcion.dataset.score);
    }
  };  