## Aplicación de escritorio que interactúa con una base de datos con más de 7000 cuentos. A través de la aplicación se puede:
+ Realizar consultas sobre los registros existentes
+ Acceder al contenido de uno de los registros para poder leerlo
+ Redactar un nuevo cuento y guardarlo en la base de datos junto a todos los demás
+ Eliminar todos los cuentos guardados a partir del 7240, es decir, todos los cuentos guardados mediante la aplicación.

## Ejecución de la Aplicación

Para ejecutar la aplicación, sigue estos pasos:

1.  Asegúrate de tener Maven y Java instalado en tu sistema.
2.  Clona este repositorio.
3.  Navega al directorio raíz del proyecto.
4.  Ejecuta el siguiente comando:

    mvn clean compile exec:java -Dexec.mainClass="com.dafnis.app.App"

Esto compilará y ejecutará la aplicación.

# Realizada con JavaFX y SceneBuilder.

## Base de Datos

La aplicación utiliza una base de datos SQLite3 para almacenar los cuentos. La base de datos se encuentra en el archivo `cuentos.db` dentro del directorio resources del proyecto. Para conectarse a la base de datos, se utiliza el driver JDBC de SQLite.

## Estructura del Proyecto

* `src/main/java/com/dafnis/app`: Contiene el código fuente de la aplicación.
    * `controllers/`: Controladores JavaFX.
    * `models/`: Clases de modelo para los cuentos.
    * `auxiliares/`: Clase auxiliar para la gestión de la tabla de resultados.
    * `db/`: Clase para la interacción con la base de datos
    * `App.java`: Punto de entrada de la aplicación.
* `src/main/resources/`: Contiene los recursos de la aplicación (archivos FXML, hojas de estilo CSS y la base de datos cuentos.db)

