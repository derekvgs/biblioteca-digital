# Sistema de Gestión de Biblioteca Digital - "Letras y Páginas"

**Curso:** ISB-32 Programación Avanzada  
**Profesor:** Ing. Roberto Antonio Jacamo Soto  
**Estudiante:** Derek Vargas 
**Entrega:** Proyecto III - Sistema Avanzado de Gestión de Biblioteca

---

## Descripción del Proyecto

Aplicación para gestionar el inventario de libros de una biblioteca digital. Permite registrar, editar, eliminar libros, asignarlos a categorías y realizar búsquedas por título.  
El backend está desarrollado en Java usando Servlets y JDBC, y se conecta a una base de datos MySQL.

---

## Requisitos Previos

- JDK 8 o superior
- MySQL (vía XAMPP o instalación local)
- Visual Studio Code o IntelliJ IDEA
- Navegador web moderno (para el frontend)

---

## Estructura del Proyecto

```plaintext
BibliotecaDigital/
│
├── src/                # Código fuente
│   ├── modelo/         # Clases de entidades
│   ├── dao/            # Acceso a datos
│   ├── conexion/       # Conexión a la BD
│   ├── handlers/       # Servlets
│   └── util/           # Utilidades
│
├── web/                # Archivos frontend (HTML, JS, CSS)
├── lib/                # Librerías externas (MySQL Connector, Gson, Servlet API)
├── scripts_sql/        # Script para crear base de datos
├── uml_modelo_entidad_relacion_biblioteca.png
└── config/db.properties
Configuración Inicial
Base de Datos
Iniciar XAMPP y activar MySQL

Acceder a http://localhost/phpmyadmin

Crear la base biblioteca_digital

Ejecutar el script SQL desde:
scripts_sql/creacion_bd.sql.sql

Configuración de Conexión
Editar config/db.properties:

properties
Copiar
Editar
db.host=localhost
db.port=3306
db.name=biblioteca_digital
db.user=root
db.password=
Compilación y Ejecución (Modo Consola)
bash
Copiar
Editar
# Crear carpeta de compilados
mkdir bin

# Compilar Java
javac -cp "lib/mysql-connector-j-9.2.0.jar" -d bin src/conexion/*.java
javac -cp "bin;lib/mysql-connector-j-9.2.0.jar" -d bin ^
    src/dao/*.java ^
    src/modelo/*.java ^
    src/util/*.java ^
    src/ui/*.java

# Copiar archivo de configuración
cp -r config bin/

# Ejecutar la aplicación desde consola
java -cp "bin;lib/mysql-connector-j-9.2.0.jar" ui.Principal
Despliegue Web con Tomcat
Compilar todos los .java en WEB-INF/classes

Copiar la carpeta web/ al directorio tomcat/webapps/BibliotecaDigital/

Iniciar Apache Tomcat

Acceder desde:
http://localhost:8080/BibliotecaDigital/web/index.html

Bibliotecas Usadas
mysql-connector-j-9.2.0.jar – Conexión con MySQL

gson-2.10.1.jar – Conversión JSON ↔ Java

javax.servlet-api-4.0.1.jar – API de servlets Java

Diagrama UML
Incluye uml_modelo_entidad_relacion_biblioteca.png, que refleja la estructura entre libros y categorías.

Agradecimiento
Gracias profesor Jacamo por fomentar el uso de buenas prácticas y herramientas que no solo funcionan en clase, sino que también se proyectan a nivel profesional. Esta guía nos ayudó a construir más allá de lo básico.
Conecté mi primera pasantía recientemente, y su impulso a que lo hiciéramos fue clave. Aprecio el enfoque práctico y su estilo directo.
