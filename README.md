# Retro Games Library

### Introducción

Retro Games Library es un gestor de juegos multiusuario para varias plataformas de videoujuegos. La aplicación te permite listar los juegos, las plataformas, los emuladores y los usuarios. También permite visualizar información y capturas de pantalla de cada juego. Además, el gestor permite lanzar los juegos con su respectivo emulador previamente instalado y configurado en la aplicación.

Cada usuario puede tener su propia colección de juegos.

Por defecto, la aplicación inserta 5 plataformas para poder empezar fácilmente a insertar juegos, pero pueden añadirse todas las que se deseen. Estas plataformas pueden ser eliminadas o editadas. Las plataformas que incorpora son:

- Nintendo NES
- Sega Master System II
- Amstrad CPC 6128
- Spectrum ZX 128
- Atari 2600

La gestión de datos se realiza, por una parte mediante tres bases de datos relacionales que guardan la información de los juegos y plataformas, la información de los usuarios y la información de los emuladores, y por otra parte haciendo uso de una base de datos noSQL para la importación y exportación de datos (aún no está implementado).

También se hace uso de un fichero de propiedades, donde se almacena la configuración de la aplicación.

### Gestión de ficheros

La información de la conexión a la base de datos de usuarios se almacena en el fichero config.conf en la raíz de la aplicación. Aparte de esta información, también se guarda el usuario actual (siempre y cuando no cierre la sesión) para próximos inicios, así como los últimos directorios en los FileChoosers de la aplicación.

### Gestión de bases de datos

La gestión de las bases de datos se realiza mediante el patrón de diseño DAO. Éste se encarga de, mediante interfaces, gestionar la conexión a las diferentes bases de datos y en diferentes tecnologías.

#### Bases de datos relacionales

###### H2 + Hibernate

La información de juegos y plataformas está definida en dos tablas:

- Game
    - ID -> Identificador del juego.
    - PlatformID -> Identificador de la plataforma del juego.
    - Title -> Título del juego.
    - Description -> Descripción del juego.
    - Year -> Año de lanzamiento del juego.
    - Gender -> Género del juego.
    - Screenshot -> Ruta a la imagen del juego.
    - Path -> Ruta al juego en el disco.
    - Command -> Comando para el lanzamiento del juego.

- Platform
    - ID -> Identificador de la plataforma.
    - Name -> Nombre de la plataforma.
    - Model -> Modelo de la plataforma.
    - Company -> Compañía de la plataforma.
    - Year -> Año de lanzamiento de la plataforma.
    - Country -> País de la sede.

###### MariaDB + JDBC

Una tabla que se encarga de la información de los usuarios:

- User
    - FirstName -> Nombre del usuario.
    - LastName -> Apellidos del usuario.
    - Birthdate -> Fecha de nacimiento del usuario.
    - Email -> Email del usuario.
    - Passw -> Contraseña del usuario.

###### OpenDB + JPA

Una tabla que se encarga de los datos de los emuladores:

- Emulator
    - ID -> Identificador del emulador.
    - Name -> Nombre del emulador.
    - Path -> Ruta al ejecutable.

### Funcionalidades

A continuación se detallan las funcionalidades de la aplicación:

- Menú archivo
    - Añadir nuevo juego: Abre un diálogo para añadir un nuevo juego a la base de datos Game.
    - Importar: (aún está por implementar)
    - Exportar: (aún está por implementar)
    - Cerrar sesión: Cierra la sesión del usuario actual y muestra la ventana de login.
    - Salir: Sale de la aplicación.
- Menú editar
    - Editar juego: Abre un diálogo con los datos del juego seleccionado para editarlo.
    - Eliminar juego: Muestra un diálogo para confirmar la eliminación del juego seleccionado.
- Menú ver
    - Plataformas: Abre una ventana con el listado de plataformas y permite añadir, editar y eliminar.
    - Usuarios: Abre una ventana con el listado de usuarios registrados y permite añadir, editar y eliminar.
- Menú opciones
    - Emuladores: Abre una ventana con el listado de emuladores y permite añadir, editar y eliminar.
    - Preferencias: (aún está por implementar)
- Menú ayuda
    - Acerca de: Muestra información de la aplicación como una breve descripción, la versión, el autor, las versiones de java y la licencia.
- Botón +: Abre un diálogo para añadir un nuevo juego a la base de datos Game.
- Botón ►: (aún está por implementar)
- Buscador: Muestra solo los juegos cuyo título contiene la ocurrencia. Se actualiza conforme se va introduciendo el texto.
- Panel derecho: Muestra toda la información del juego seleccionado.
