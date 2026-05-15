## Prompt 1: Mejora visual del MenuPrincipalScreen con Drawer y TopAppBar

Quiero mejorar visualmente el archivo `@MenuPrincipalScreen.kt` de mi app Android llamada **GymTracker Pro**, desarrollada con **Kotlin, Jetpack Compose, Material 3, Room SQLite y Navigation Compose**.
La lógica de navegación ya funciona, por eso no debes cambiar los parámetros del composable ni modificar otros archivos. Mantén exactamente la firma:

```kotlin
@Composable
fun MenuPrincipalScreen(
    usuarioId: Int,
    onNavigateToAgregarRutina: () -> Unit,
    onNavigateToListaRutinas: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onLogout: () -> Unit
)

Necesito que la pantalla se parezca a la imagen de referencia del menú principal.
 Debe usar ModalNavigationDrawer, Scaffold y una TopAppBar azul oscuro con ícono de menú a la izquierda, el título “GymTracker Pro” y un ícono de notificación a la derecha.
Al presionar el ícono de menú, debe abrirse el drawer lateral. 
El drawer debe mostrar un avatar circular con las iniciales reales del usuario logueado, el nombre real del usuario y su correo real.
 Estos datos deben obtenerse desde Room usando el usuarioId recibido por parámetro y el método usuarioDao().buscarPorId(usuarioId). 
No coloques datos fijos como “JP”, “Juan Pérez” o “juan@gmail.com”.
El drawer debe tener las opciones Inicio, Agregar rutina, Mis rutinas, Mi perfil y Cerrar sesión. 
Cada opción del drawer debe ejecutar su función correspondiente: “Agregar rutina” debe llamar a onNavigateToAgregarRutina(), “Mis rutinas” a onNavigateToListaRutinas(), “Mi perfil” a onNavigateToPerfil() y “Cerrar sesión” a onLogout().
En el contenido principal debe mostrarse el saludo “Hola,” y debajo el nombre real del usuario logueado, obtenido desde Room.
 Luego debe mostrar cuatro tarjetas en una cuadrícula de dos columnas: Agregar rutina, Mis rutinas, Mi perfil y Cerrar sesión.
Cada tarjeta debe tener un ícono, texto centrado, esquinas redondeadas, color suave diferente y ejecutar la acción correspondiente al tocarla.
No uses librerías externas. Usa solo Jetpack Compose y Material 3. Genera el código completo y funcional del archivo MenuPrincipalScreen.kt.
No modifiques Room, DAOs, AppNavigation ni otros archivos; solo mejora esta pantalla manteniendo la lógica actual funcionando.



## Prompt 2: Mejora visual del AgregarRutinaScreen

Quiero mejorar visualmente el archivo `@AgregarRutinaScreen.kt` de mi app Android llamada **GymTracker Pro**, desarrollada con **Kotlin, Jetpack Compose, Material 3, Room SQLite y Navigation Compose**.

La lógica de creación de rutina ya debe mantenerse funcionando, por eso no debes cambiar los parámetros del composable ni modificar otros archivos. Mantén exactamente la firma:

```kotlin
@Composable
fun AgregarRutinaScreen(
    usuarioId: Int,
    onRutinaGuardada: () -> Unit,
    onBack: () -> Unit
)
Debe usar Scaffold y una TopAppBar azul oscuro con flecha de volver a la izquierda, el título “Nueva rutina” y un ícono de guardar o calendario a la derecha. 
El formulario debe tener un diseño limpio, con fondo claro, buena separación entre campos y bordes redondeados.

La pantalla debe incluir los siguientes campos: Ejercicio, Grupo muscular, Series, Repeticiones, Peso (kg) y Fecha.
El campo Ejercicio debe ser un OutlinedTextField con ejemplo “Press banca”.
El campo Grupo muscular puede verse como un campo desplegable o un campo de texto con estilo similar al de la imagen, usando ejemplo “Pecho”. 
Los campos Series y Repeticiones deben aparecer en una misma fila, uno al lado del otro, como en la imagen. El campo Peso (kg) debe aceptar valores decimales, por ejemplo “60.5”. El campo Fecha debe mostrar un ejemplo como “12/05/2026” y puede tener un ícono de calendario al lado derecho.
La lógica debe mantenerse igual: al presionar el botón Guardar rutina, se deben validar los campos, construir un objeto Rutina usando el usuarioId recibido desde el login, llamar a rutinaDao().insertar(rutina) y luego ejecutar onRutinaGuardada() para volver a la pantalla anterior. Si algún campo está vacío o si series, repeticiones o peso no son numéricos, debe mostrarse un mensaje de error.
El botón Guardar rutina debe estar al final del formulario, ocupar todo el ancho, tener color azul oscuro, texto blanco, esquinas redondeadas y altura similar a la imagen. No uses librerías externas. Usa solo Jetpack Compose y Material 3. Genera el código completo y funcional del archivo AgregarRutinaScreen.kt. No modifiques Room, DAOs, AppNavigation ni otros archivos; solo mejora esta pantalla manteniendo la lógica actual funcionando.

## Prompt 3: Mejora visual del ListaRutinasScreen

Quiero mejorar visualmente el archivo `@ListaRutinasScreen.kt` de mi app Android llamada **GymTracker Pro**, desarrollada con **Kotlin, Jetpack Compose, Material 3, Room SQLite y Navigation Compose**.

La lógica de listado y eliminación de rutinas ya debe mantenerse funcionando, por eso no debes cambiar los parámetros del composable ni modificar otros archivos. Mantén exactamente la firma:

```kotlin
@Composable
fun ListaRutinasScreen(
    usuarioId: Int,
    onNavigateToAgregar: () -> Unit,
    onNavigateToDetalle: (Int) -> Unit,
    onBack: () -> Unit
)

Necesito que la pantalla se parezca a la imagen de referencia de Mis rutinas. Debe usar Scaffold y una TopAppBar azul oscuro con flecha de volver a la izquierda, el título “Mis rutinas” y un ícono de búsqueda a la derecha. La pantalla debe tener fondo claro y mostrar las rutinas en tarjetas con bordes redondeados y sombra suave.

La lógica debe mantenerse igual: la pantalla debe usar LazyColumn para recorrer las rutinas obtenidas desde Room con rutinaDao().listarPorUsuario(usuarioId). Solo deben mostrarse las rutinas del usuario logueado. Cada tarjeta debe mostrar el nombre del ejercicio en negrita, el grupo muscular debajo en color azul o gris, y una línea resumen con series, repeticiones, peso y fecha, por ejemplo: “4 series × 12 reps · 60.5 kg · 12/05/2026”.

Cada tarjeta debe tener un ícono de lápiz para editar y un ícono de basura para eliminar. El ícono de lápiz debe llamar a onNavigateToDetalle(rutina.id) para navegar a DetalleRutinaScreen/{rutinaId}. El ícono de basura debe mostrar un AlertDialog de confirmación antes de eliminar. Si el usuario confirma, debe llamar a rutinaDao().eliminar(rutina) y luego recargar la lista.

También debe incluir un FloatingActionButton circular azul en la esquina inferior derecha con un ícono de “+”. Al presionarlo debe llamar a onNavigateToAgregar() para navegar a la pantalla de agregar rutina.

Si no hay rutinas registradas, debe mostrarse un mensaje centrado indicando “Aún no tienes rutinas registradas”. No uses librerías externas. Usa solo Jetpack Compose y Material 3. Genera el código completo y funcional del archivo ListaRutinasScreen.kt. No modifiques Room, DAOs, AppNavigation ni otros archivos; solo mejora esta pantalla manteniendo la lógica actual funcionando.

## Prompt 4: Mejora visual del DetalleRutinaScreen

Quiero mejorar visualmente el archivo `@DetalleRutinaScreen.kt` de mi app Android llamada **GymTracker Pro**, desarrollada con **Kotlin, Jetpack Compose, Material 3, Room SQLite y Navigation Compose**.

La lógica de edición y eliminación de rutina ya debe mantenerse funcionando, por eso no debes cambiar los parámetros del composable ni modificar otros archivos. Mantén exactamente la firma:

```kotlin
@Composable
fun DetalleRutinaScreen(
    rutinaId: Int,
    onBack: () -> Unit,
    onRutinaActualizada: () -> Unit,
    onRutinaEliminada: () -> Unit
)

Necesito que la pantalla se parezca a la imagen de referencia de Editar rutina. Debe usar Scaffold y una TopAppBar azul oscuro con flecha de volver a la izquierda, el título “Editar rutina #id” o “Editar rutina”, y un ícono de basura a la derecha para eliminar la rutina. El diseño debe tener fondo claro, campos con bordes redondeados, buena separación entre elementos y un estilo limpio.

La lógica debe mantenerse igual: la pantalla recibe rutinaId por argumento de navegación. En LaunchedEffect(rutinaId) debe cargar la rutina usando rutinaDao().buscarPorId(rutinaId) y llenar los campos del formulario con los datos existentes. Al presionar el botón Actualizar cambios, debe validar los campos, convertir series y repeticiones a Int, convertir peso a Double, y luego llamar a rutinaDao().actualizar(rutina.copy(...)). Después de actualizar correctamente debe ejecutar onRutinaActualizada().

La pantalla debe mostrar una pequeña etiqueta o aviso superior con texto parecido a “Modificando registro existente”, con fondo azul claro o gris claro, como en la imagen. Debe incluir los campos Ejercicio, Grupo muscular, Series, Repeticiones, Peso (kg) y Fecha. Los campos Series y Repeticiones deben aparecer en una misma fila, uno al lado del otro. El campo Peso (kg) debe aceptar valores decimales. El campo Fecha debe mantener el valor cargado de la rutina.

El botón Actualizar cambios debe estar al final del formulario, ocupar todo el ancho, tener color verde oscuro, texto blanco, esquinas redondeadas y altura similar a la imagen. Si algún campo está vacío o si series, repeticiones o peso no son numéricos, debe mostrarse un mensaje de error.

El ícono de basura en la TopAppBar debe permitir eliminar la rutina desde esta pantalla. Antes de eliminar debe mostrar un AlertDialog de confirmación. Si el usuario confirma, debe llamar a rutinaDao().eliminar(rutina) y luego ejecutar onRutinaEliminada().

No uses librerías externas. Usa solo Jetpack Compose y Material 3. Genera el código completo y funcional del archivo DetalleRutinaScreen.kt. No modifiques Room, DAOs, AppNavigation ni otros archivos; solo mejora esta pantalla manteniendo la lógica actual funcionando.

## Prompt 5: Mejora visual del PerfilUsuarioScreen

Quiero mejorar visualmente el archivo `@PerfilUsuarioScreen.kt` de mi app Android llamada **GymTracker Pro**, desarrollada con **Kotlin, Jetpack Compose, Material 3, Room SQLite y Navigation Compose**.

La lógica del perfil ya debe mantenerse funcionando, por eso no debes cambiar los parámetros del composable ni modificar otros archivos. Mantén exactamente la firma:

```kotlin
@Composable
fun PerfilUsuarioScreen(
    usuarioId: Int,
    onBack: () -> Unit,
    onLogout: () -> Unit
)

Necesito que la pantalla se parezca a la imagen de referencia de Mi perfil. Debe usar Scaffold y una TopAppBar azul oscuro con flecha de volver a la izquierda y el título “Mi perfil”. La pantalla debe tener fondo claro, diseño limpio, elementos centrados y estilo moderno.

La lógica debe mantenerse igual: la pantalla recibe usuarioId, carga los datos del usuario usando usuarioDao().buscarPorId(usuarioId) y muestra sus datos reales, como nombre completo, nombre de usuario, email y edad. No coloques datos fijos como “Juan Pérez Vela”, “@jperez” o “juan@mail.com”; deben venir desde Room según el usuario logueado.

En la parte superior del contenido debe mostrarse un avatar circular grande con las iniciales reales del usuario logueado. Debajo debe mostrarse el nombre completo del usuario en negrita y, debajo, su nombre de usuario con formato tipo @usuario.

Después deben aparecer dos tarjetas pequeñas en una fila: una para mostrar la cantidad total de rutinas registradas y otra para mostrar los kg totales o volumen total. Estos datos deben calcularse usando los métodos existentes del DAO, equivalentes a SELECT COUNT(*) FROM rutinas WHERE usuario_id = :id y SELECT SUM(peso_kg * series * repeticiones) FROM rutinas WHERE usuario_id = :id.

Más abajo debe mostrarse una sección de información del usuario con filas separadas por líneas divisorias. Cada fila debe tener un ícono pequeño a la izquierda, un texto de etiqueta y el valor a la derecha. Las filas deben ser: Email, Edad y Miembro desde. Si el modelo Usuario no tiene campo de fecha de registro, puedes mostrar un texto fijo simple como “Registrado” o no mostrar esa fila, pero no modifiques la entidad ni la base de datos.

Al final de la pantalla debe haber un botón grande de Cerrar sesión, con borde o fondo rojo suave, texto rojo o blanco según el diseño, esquinas redondeadas y un ícono de salida. Al presionarlo debe ejecutar onLogout(), manteniendo la navegación actual hacia Login definida en AppNavigation.

No uses librerías externas. Usa solo Jetpack Compose y Material 3. Genera el código completo y funcional del archivo PerfilUsuarioScreen.kt. No modifiques Room, DAOs, AppNavigation ni otros archivos; solo mejora esta pantalla manteniendo la lógica actual funcionando.