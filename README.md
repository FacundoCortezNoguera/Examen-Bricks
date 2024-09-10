# Examen-Bricks

Esta es una aplicación de Spring Boot que administra productos y categorías para un comercio. La aplicación incluye un conjunto de APIs RESTful para gestionar productos y categorías, utilizando una base de datos en memoria y caché para mejorar el rendimiento.

## Requisitos previos

Antes de comenzar, asegúrate de tener instaladas las siguientes herramientas:

- **Java 17** o superior
- **Gradle** (dependiendo de tu configuración de construcción)
- **Git** (opcional, para clonar el proyecto)
- **IDE** como IntelliJ IDEA, Eclipse, o Visual Studio Code (opcional, para desarrollo)

## Clonar el proyecto

Si no tienes el código del proyecto, clónalo desde el repositorio:

```
git clone https://github.com/FacundoCortezNoguera/Examen-Bricks
cd Examen-Bricks
```
## Instalar dependencias
```
gradlew.bat build
```

## Ejecutar la aplicación
```
gradlew.bat bootRun
```

## Acceder a la API
Endpoints:
```
Post:
http://localhost:8080/product

Delete:
http://localhost:8080/api/product/{id}

Get:
http://localhost:8080/product?

Get-id:
http://localhost:8080/api/product/{id}

Put:
http://localhost:8080/api/product/{id}

Get-Category
http://localhost:8080/category
```
