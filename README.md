<h1 align="center"> :video_game: GAME APLICATION :joystick: </h1>

<p align="center">
  <img src="https://github.com/user-attachments/assets/a2e93281-50c0-45d0-ad63-285b1c1bacb7" alt="Descripción de la imagen" width="300">
</p>

![Kotlin](https://img.shields.io/badge/Kotlin-1.8.10-blueviolet?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.4.0-brightgreen?logo=jetpackcompose&logoColor=white)
![Architecture-MVVM](https://img.shields.io/badge/Architecture-MVVM-blue)
![Retrofit](https://img.shields.io/badge/Retrofit-2.9.0-yellow?logo=retrofit)
![Coroutines](https://img.shields.io/badge/Coroutines-1.7.0-purple?logo=kotlin&logoColor=white)
![Room](https://img.shields.io/badge/Room-2.5.0-red?logo=android&logoColor=white)
![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-2.45-yellowgreen?logo=android&logoColor=white)
![DataStore](https://img.shields.io/badge/DataStore-1.1.1-orange?logo=android&logoColor=white)
![Coil](https://img.shields.io/badge/Coil-2.4.0-tomato?logo=coil&logoColor=white)
![BiometricPrompt](https://img.shields.io/badge/BiometricPrompt-1.2.0alpha05-cyan?logo=android&logoColor=white)
![Lottie](https://img.shields.io/badge/Lottie-6.0.0-lightgreen.svg?logo=lottie&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-1.2.1-salmon?logo=android&logoColor=white)
![Min SDK Version](https://img.shields.io/badge/Min%20SDK%20Version-24-lightblue)

![Badge en Desarollo](https://img.shields.io/badge/Estatus-En%20Desarrollo-green)

## ¡Bienvenido a la Experiencia Definitiva de Videojuegos! :exploding_head:
¿Eres un apasionado de los videojuegos? ¿Te encanta descubrir nuevos títulos y sumergirte en aventuras épicas? Entonces, estás en el lugar correcto. Te presento Games App que te conecta con el fascinante mundo de los videojuegos.

🎮 **Explora, Descubre y Juega**

Nuestra aplicación te ofrece una interfaz intuitiva y atractiva que te permite explorar una vasta colección de videojuegos.<br>
Desde los clásicos que marcaron época hasta los lanzamientos más recientes, aquí encontrarás todo lo que necesitas para satisfacer tu sed de aventura.

🌟 **Características Inigualables**

- **Información Detallada**: Conoce la trama, developers, puntuaciones y plataformas disponibles de tus títulos favoritos.
- **Lanzamientos Recientes**: Recibe información acerca de los nuevos lanzamientos y juegos que estén en tendencia.
- **Navegación Intuitiva**: Disfruta de una experiencia fluida mientras navegas por nuestras secciones.

## Descripción :open_book:

El proyecto es una aplicación Kotlin diseñada con Jetpack Compose y usando las arquitecturas MVVM en conjunto con Clean Architecture, la cual consume la API https://api.rawg.io/docs/ :link: de la web [RAWG :video_game:](https://rawg.io/) la cual es una base de datos con más de 350,000 juegos en su catálogo, disponible para que 
cualquier desarrollador pueda hacer uso de ella en sus proyectos y acceda a todo su contenido, desde próximos lanzamientos, los juegos más votados, favoritos del público, etc.

En nuestra aplicación podrás encontrar información detallada de cada título, género o plataforma, así como también crear tu cuenta para registrar tus títulos favoritos. Con una interfaz moderna, animaciones que enriquecen la experiencia y fácil de navegar,
podrás explorar una vasta colección de juegos y tener datos sobre ellos siempre al alcance de tu mano. 

### Características clave

* **Exploración de títulos, géneros y plataformas**:<br>

Accede a detalles completos acerca de diversos títulos, tanto recientes como los mejores calificados, información sobre gran cantidad de plataformas que han existido a lo largo de los   años e igualmente navega a través de una amplia cantidad de géneros, desde acción y aventura hasta rol y simulación. Aquí podrás encontrar descripciones detalladas sobre cada uno,   quienes fueron los desarrolladores y publishers, la puntuación que han obtenido hasta el momento por parte de los usuarios, cantidad de jugadores, etc. 

* **Videojuegos favoritos**:<br>

Ten la posibilidad de agregar cualquier videjuego a un apartado de favoritos en caso de que algún título te haya interesado o estés pensando en saber más acerca de tu próxima aventura.

* **Animaciones de navegación e interfaz**:<br>

Cada flujo en la aplicación cuenta con su propia animación al cambiar de una pantalla a otra. Además podrás visualizar pequeñas animaciones y escuchar diversos sonidos :loud_sound: relacionados con el mundo del gaming en las interacciones que tengas con los componentes de la aplicación, esto con el fin de mejorar tu experiencia y sea más atractiva haciéndote sentir como si estuvieras realmente en un videojuego.

* **Creación de cuenta de usuario**:<br>

Podrás crearte un usuario eligiendo un user name (podrás hacer uso de tu gamertag :wink:) y un avatar o imagen de perfil (próximamente habrá más disponibles) con el cual iniciarás sesión en la app e igualmente podrás editar tu información más adelante si así lo deseas.

* **Autenticación biométrica**:<br>

¿Deseas poder hacer uso de tu huella para iniciar sesión :eyes::eyes::eyes:? ¡Es posible!, si tu dispositivo es compatible con los datos biométricos podrás ingresar a la aplicación haciendo uso de tu huella y así acceder a toda tu información.
  
* **Almacenamiento local de datos utilizando *ROOM***:<br>

Los diferentes usuarios que creaste, juegos favoritos y listado de juegos que obtengas de la api serán guardados de manera local con ROOM para acceder a esta información en caso de que no cuentes con acceso a internet.

* **Consumo de API a través de *Retrofit***:<br>

Nuestra aplicación se conecta a una API externa utilizando Retrofit, una potente y flexible biblioteca de cliente HTTP en Kotlin. Gracias a Retrofit, podemos realizar llamadas a la API de manera eficiente y segura, permitiendo que los usuarios accedan a información actualizada sobre videojuegos.

* **Personaliza tu tema**:<br>

Elige el tema que más te guste, podrás elegir entre configurar el tema claro :sun_with_face:, oscuro :new_moon_with_face: o que se adapte de acuerdo a las configuraciones de tu dispositivo :iphone:

## Tecnologías utilizadas :iphone:
* **Jetpack Compose**: Toolkit moderno de UI de Android que permite construir interfaces de usuario de manera declarativa.
* **Clean Architecture**: Organiza el código en capas bien definidas, promoviendo la separación de responsabilidades. Facilita la escalabilidad y el mantenimiento.
* **MVVM**: Enfoque arquitectónico que desacopla la lógica de negocio de la interfaz de usuario. Facilita la creación de interfaces reactivas con una clara separación de responsabilidades entre las capas.
* **Retrofit**: Librería de cliente HTTP que permite realizar solicitudes HTTP/REST en Android. Facilita el consumo de APIs y convierte las respuestas JSON en objetos Kotlin para su fácil manipulación.
* **Dagger Hilt**: Biblioteca de inyección de dependencias que simplifica la creación y gestión de dependencias en Android.
* **Coroutines**: Son una forma avanzada y sencilla de manejar tareas asíncronas. Facilitan la ejecución de operaciones de larga duración, como solicitudes de red o tareas de I/O, sin bloquear el hilo principal.
* **Navigation Compose**: Permite gestionar la navegación entre pantallas de manera declarativa dentro de aplicaciones construidas con Jetpack Compose.
* **Material Design 3**: Ofrece una experiencia de usuario moderna y coherente a través de temas personalizables, componentes de UI optimizados y nuevas directrices para interfaces accesibles y atractivas.
* **Coil**: Biblioteca ligera para la carga de imágenes en Android. Proporciona una forma rápida y eficiente de mostrar imágenes en la UI.
* **DataStore**: Permite almacenar datos de preferencias o claves-valor de manera eficiente y segura, con un enfoque basado en Kotlin coroutines y Flow.
* **ROOM**: Biblioteca de persistencia local de datos de Android basada en SQLite. Proporciona una capa de abstracción que facilita el acceso a la base de datos, integrándose de manera sencilla con coroutines y LiveData.
* **Lottie**: Biblioteca para Android que permite reproducir animaciones de alta calidad exportadas desde After Effects en formato JSON.

## Estructura del proyecto

## Requisitos

1. Android Studio Jellyfish | 2023.3.1 o superior
2. Gradle Version 7.5
3. Kotlin 1.8.10
4. Android API 24 o superior (Android 7+)
5. API Key

## Instalación

1. Clona el repositorio:
   ```
    git clone https://github.com/Deivid117/Games-App.git
2. Obtén tu API Key :key: registrándote en la siguiente página
https://rawg.io/apidocs

3. Agrega tu API Key en el archivo ***local.properties*** de esta manera<br>
***API_KEY="tu api key"***

4. Ejecuta el proyecto :rocket:

## Capturas

### Modo Claro :sun_with_face:
<p align="center">
  <img src="https://github.com/user-attachments/assets/cab0a0d8-efc4-4eb9-bd2c-54024131325f" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/6bbcdcaf-ff00-4bf9-aaca-b429018608da" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/70a126bc-dc14-4ebb-a64b-11e8a9d51331" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/a8cd5098-d3bf-4164-9d4c-18f846132fb6" alt="Descripción de la imagen">
</p>

### Modo Oscuro :new_moon_with_face:
<p align="center">
  <img src="https://github.com/user-attachments/assets/eee03721-1cd6-47c0-afd1-d6c290fe64f3" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/1ebeab9c-321f-4975-8428-4641a9795128" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/9c665b8f-4602-403a-86b0-8d4e00f5585f" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/1764f1af-50ca-456b-9c4f-e5de87dc7d92" alt="Descripción de la imagen">
</p>

## Video demostrativo

## Licencia

![License](https://img.shields.io/badge/License-MIT-green)

## Autor :man_technologist:

*David Huerta* :copyright:	2024<br>
email :email:: deivid.was.here117@gmail.com<br>
linkedin :man_office_worker:: [Perfil LinkedIn](https://www.linkedin.com/in/david-de-jes%C3%BAs-ju%C3%A1rez-huerta-159695241/)

