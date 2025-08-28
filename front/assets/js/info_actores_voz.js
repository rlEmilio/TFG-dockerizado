document.addEventListener("DOMContentLoaded", ()=>{

  //id, personaje, anime
  let arrayTrabajos = [
    [123, "Roronoa Zoro", "One Piece"], [123, "Hijikata Toushiro", "Gintama"], [123, "Mugen", "Samurai Champloo"], [99, "Celty Sturluson", "Durarara"], [99, "Suruga Kanbaru", "Bakemonogatari"], [99, "Bishamon", "Noragami"], [82, "Hitsugaya Toushiro", "Bleach"], [82, "Edward Elric", "Fullmetal Alchemist Brotherhood"], [82, "Hange Zoe", "Shingeki no Kyojin"], [8, "Kagura", "Gintama"], [8, "Alphonse Elric", "Fullmetal Alchemist Brotherhood"], [8, "Aisaka Taiga", "Toradora"],[2, "Sakata Gintoki", "Gintama"], [2, "Escanor", "Nanatsu no Taizai"], [2, "Charlotte Katakuri", "One Piece"], [2, "Sakamoto Tarou", "Sakamoto Days"], [79, "Arataka Reigen", "Mob Psycho 100"], [79, "Griffith", "Berserk"], [79, "Giyuu Tomioka", "Kimetsu no Yaiba"], [79, "Suguru Getou", "Jujutsu Kaisen"], [95, "Sukehiro Yami", "Black Clover"], [95, "Ryoumen Sukuna", "Jujutsu Kaisen"], [95, "Aomine Daiki", "Kuroko no Basket"], [212, "Erwin Smith", "Shingeki no Kyojin"], [212, "Kuujou Joutarou", "Jojo no Kimyou na Bouken"], [212, "Handa Sei", "Barakamon"], [212, "Hori Kyousuke", "Horimiya"], [125, "Sanji", "One Piece"], [125, "Nanba Mutta", "Uchuu Kyoudai"], [125, "Benny", "Black Lagoon"], [513, "Satoru Gojo", "Jujutsu Kaisen"], [513, "Okazaki Tomoya", "Clannad"], [513, "Tetsurou Kuroo", "Haikyuu"], [270, "Katsuki Bakugou", "Boku no Hero Academia"], [270, "Takumi Usui", "Kaichou wa Maid-sama!"], [270, "Akabane Karma", "Ansatsu Kyoushitsu"], [11817, "Yukihira Souma", "Shokugeki no Souma"], [11817, "Hashibira Inosuke", "Kimetsu no Yaiba"], [11817, "Kirigaya Kazuto", "Sword Art Online"], [118, "Levi", "Shingeki no Kyojin"], [118, "Yato", "Noragami"], [118, "Koyomi Araragi", "Bakemonogatari"], [118, "Trafalgar Law", "One Piece"], [185, "Shiina Mayuri", "Steins;Gate"], [185, "Tachibana Kaede", "Angel Beats"], [185, "Sengoku Nadeko", "Bakemonogatari"], [185, "Tsunemori Akane", "Psycho-Pass"], [65, "Okabe Rintarou", "Steins;Gate"], [65, "Yagami Light", "Death Note"], [65, "Shinmon Benimaru", "Enen no Shouboutai"], [672, "Eren Yeager", "Shingeki no Kyojin"], [672, "Kozume Kenma", "Haikyuu"], [672, "Todoroki Shouto", "Boku no Hero Academia"], [34785, "Megumin", "KonoSuba"], [34785, "Emilia", "Re:Zero Kara Hajimeru Isekai Seikatsu"], [34785, "Hoshino Ai", "Oshi no Ko"], [6686, "Maomao", "Kusuriya no Hitorigoto"], [6686, "Hinazuki Kayo", "Boku Dake ga Inai Machi"], [6686, "Tatsumaki", "One Punch Man"], [160, "Dio Brando", "Jojo no Kimyou na Bouken"], [160, "Takasugi Shinsuke", "Gintama"], [160, "Zeke", "Shingeki no Kyojin"], [16635, "Kaneki Ken", "Tokyo Ghoul"], [16635, "Kamado Tanjirou", "Kimetsu no Yaiba"], [16635, "Arima Kousei", "Shigatsu wa Kimi no Uso"], [16635, "Takakura Ken", "Dandadan"], [75, "Monkey D. Luffy", "One Piece"], [75, "Turbo vieja", "Dandadan"], [75, "Krillin", "Dragon Ball"], [557, "Son Goku", "Dragon Ball"], [17, "Katsura Kotarou", "Gintama"], [17, "Gaara", "Naruto"], [17, "Akaza", "Kimetsu no Yaiba"], [13639, "Takanashi Rikka", "Chuunibyou Demo Koi ga Shita"], [13639, "Iki Hiyori", "Noragami"], [13639, "Yoshioka Futaba", "Ao Haru Ride"], [81, "Hyuuga Hinata", "Naruto"], [81, "Ayase Seiko", "Dandadan"], [81, "Lan Fan", "Fullmetal Alchemist Brotherhood"], [22, "Sakamoto Tatsuma", "Gintama"], [22, "Roy Mustang", "Fullmetal Alchemist Brotherhood"], [22, "Urahara Kisuke", "Bleach"], [22, "Kaiki Deishuu", "Bakemonogatari"], [11787, "Sakurajima Mai", "Seishun Buta Yarou"], [11787, "Hori Kyouko", "Horimiya"], [11787, "Kugisaki Nobara", "Jujutsu Kaisen"], [158, "Arlert Armin", "Shingeki no Kyojin"], [158, "Littner Yoko", "Tengen Toppa Gurren Lagann"], [158, "Yaoyorozu Momo", "Boku no Hero Academia"], [67, "Shinichi Kudou", "Meitantei Conan"], [67, "Usopp", "One Piece"], [67, "Lawliet L", "Death Note"], [17215, "Frieren", "Sousou no Frieren"], [17215, "Anya Forger", "Spy x Family"], [17215, "Hatori Chise", "Mahoutsukai no Youme"], [20, "Uzui Tengen", "Kimetsu no Yaiba"], [20, "Kamina", "Tengen Toppa Gurren Lagann"], [20, "Diavolo", "Jojo no Kimiyou na Bouken"], [21, "Hatake Kakashi", "Naruto"], [21, "Tsugikuni Yoriichi", "Kimetsu no Yaiba"], [21, "Oboro", "Gintama"], [49011, "Power", "Chainsaw Man"], [49011, "Sano Manjirou", "Tokyo Revengers"], [49011, "Kuujou Jolyne", "Jojo no Kimyou na Bouken"], [146, "Kyouraku Shunshui", "Bleach"], [146, "Rider", "Fate/Zero"], [146, "Thorkell", "Vinland Saga"], [63, "Maes Hughes", "Fullmetal Alchemist Brotherhood"], [63, "Leorio", "Hunter x Hunter"], [63, "Ladd Russo", "Baccano"], [113, "Emiya Kiritsugu", "Fate/Zero"], [113, "Yukihira Jouichirou", "Shokugeki no Souma"], [113, "Kogoro Mouri", "Meitantei Conan"], [8662, "Makise Kurisu", "Steins;Gate"], [8662, "Tomone", "Noragami"]
  ]

    const urlParams = new URLSearchParams(window.location.search);
    const idActor = urlParams.get('id_actor');

    if (idActor) {
        // Hacer el fetch para obtener los detalles del actor
        const urlApi = `http://localhost:8080/api/actores/${idActor}`;
    
        fetch(urlApi)
          .then(res => {
            if (!res.ok) throw new Error('Error en fetch: ' + res.status);
            return res.json();
          })
          .then(actor => {
            let actorImage = document.getElementById("actorImagen");
            let actorName = document.getElementById("nombreActor");
            let actorFavorites = document.getElementById("favoritosActor");
            let actorLink = document.getElementById("linkActor");
            let fecha_nacimiento = document.getElementById("fecha_nacimiento");
            let actorDescription = document.getElementById("descripcionActor");
            let trabajos = document.getElementById("trabajos");

            //Actualizo la imagen
            actorImage.setAttribute("src", actor.imagen);
            actorImage.setAttribute("alt", actor.nombre);

            //Actualizo los datos del actor
            actorName.textContent = actor.nombre;
            actorFavorites.textContent = actor.popularidad;
            actorLink.setAttribute("href", `https://myanimelist.net/people/${idActor}/`);
            actorLink.textContent = "Ver perfil completo";
            fecha_nacimiento.textContent = actor.fecha_nacimiento;
            
            for (let i = 0; i < arrayTrabajos.length; i++) {
              if (idActor == arrayTrabajos[i][0]) {
                let nuevaLista = document.createElement("ul");
                let nuevoRegistro = document.createElement("li");
                nuevoRegistro.innerText = `"${arrayTrabajos[i][1]}" en ${arrayTrabajos[i][2]}`;

                nuevaLista.appendChild(nuevoRegistro);
                
                trabajos.appendChild(nuevaLista);
              }
            }
          })
          .catch(err => {
            Swal.fire({
                title: 'Error',
                text: 'Error al conectar con el servidor: ' + err.message,
                icon: 'error',
                confirmButtonText: 'Ok'
            });
          });
      } else {
        Swal.fire({
          title: 'Error',
          text: 'No se encontró un id_actor en la URL.',
          icon: 'error',
          confirmButtonText: 'Ok'
        });
      }
})

