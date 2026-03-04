# api-spring-dev


# API Spring WebFlux – Gestión de Franquicias

## Descripción

Este proyecto implementa una API REST reactiva para la gestión de franquicias, sucursales y productos.
La aplicación está construida utilizando Spring Boot con WebFlux, siguiendo principios de Clean Architecture,
permitiendo una separación clara entre dominio, lógica de negocio e infraestructura.

La API permite:

- Crear franquicias
- Agregar sucursales a una franquicia
- Agregar productos a una sucursal
- Actualizar stock de productos
- Eliminar productos
- Consultar el producto con mayor stock por sucursal
- Actualizar nombres de franquicias, sucursales y productos

El proyecto también incluye:

- Programación reactiva con Project Reactor
- Persistencia en MongoDB
- Contenerización con Docker
- Unit tests para casos de uso

---

# Arquitectura del Proyecto

El proyecto sigue una estructura basada en Clean Architecture, separando responsabilidades en capas independientes.

src/main/java/com/wcamprog/apispringdev

domain
- Entidades del dominio (Franquicia, Sucursal, Producto)
- Excepciones del dominio

application
- Casos de uso (UseCases)
- Puertos de repositorio

infrastructure
- Implementaciones de repositorios MongoDB
- Controladores REST

ApiSpringDevApplication.java

### Capas

Domain
- Contiene las entidades del negocio
- No depende de frameworks

Application
- Implementa los casos de uso del sistema
- Orquesta la lógica de negocio

Infrastructure
- Implementación de persistencia
- Controladores REST
- Configuración de frameworks

---

# Tecnologías Utilizadas

- Java 17
- Spring Boot 3
- Spring WebFlux
- Project Reactor
- MongoDB
- Maven
- Docker
- JUnit 5
- Mockito
- Reactor Test

---

# Requisitos del Sistema

Para ejecutar el proyecto se necesita:

- Java 17 o superior
- Docker (opcional pero recomendado)
- MongoDB (si no se usa Docker)

---

# Instalación

## 1. Clonar el repositorio

git clone https://github.com/Wcamprog/api-spring-dev.git

cd api-spring-dev

---

## 2. Configuración de MongoDB

La aplicación utiliza **MongoDB** como sistema de persistencia de datos.
El proyecto permite ejecutar la base de datos en **dos modos**: local mediante Docker o en la nube utilizando **MongoDB Atlas**.

La conexión a la base de datos se configura mediante la variable de entorno:

SPRING_DATA_MONGODB_URI

Esta variable es utilizada por la aplicación en el archivo:

src/main/resources/application.properties

Contenido:

spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI}

---

### Opción 1: MongoDB local (Docker)

Para desarrollo local se puede ejecutar MongoDB dentro de Docker utilizando `docker-compose`.

En este caso la aplicación se conecta al contenedor de MongoDB mediante el nombre del servicio definido en `docker-compose`:

mongodb://mongo:27017/franquicias

Ejemplo de configuración de la variable de entorno:

SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/franquicias

---

### Opción 2: MongoDB en la nube (MongoDB Atlas)

También es posible utilizar **MongoDB Atlas**, el servicio administrado de MongoDB en la nube.

En este caso la conexión se realiza mediante un URI proporcionado por Atlas:

mongodb+srv://usuario:<password>@cluster.mongodb.net/franquicias

Ejemplo de configuración:

SPRING_DATA_MONGODB_URI=mongodb+srv://usuario:<password>@cluster.mongodb.net/franquicias

---

### Ejecución del proyecto

Para iniciar la aplicación utilizando Docker:

docker compose up --build

Dependiendo del valor configurado en `SPRING_DATA_MONGODB_URI`, la aplicación se conectará a:

* MongoDB ejecutándose localmente en Docker, o
* MongoDB Atlas en la nube.

---


# Ejecución del Proyecto

## Usando Maven Wrapper

Windows:

.\mvnw spring-boot:run

Linux / Mac:

./mvnw spring-boot:run

---

## Ejecutar el JAR

Compilar:

./mvnw clean install

Ejecutar:

java -jar target/api-spring-dev-0.0.1-SNAPSHOT.jar

---

# Ejecución de Tests

El proyecto incluye tests unitarios para los casos de uso.

Ejecutar todos los tests:

./mvnw test

Ejecutar un test específico:

./mvnw -Dtest=CrearFranquiciaUseCaseTest test

---

# Endpoints Principales

## Franquicias

Crear franquicia
POST /franquicias

Body:
{
"nombre": "Franquicia A"
}

Listar franquicias
GET /franquicias

Obtener franquicia por ID
GET /franquicias/{franquiciaId}

Actualizar nombre de franquicia
PATCH /franquicias/{franquiciaId}/nombre

---

## Sucursales

Agregar sucursal
POST /franquicias/{franquiciaId}/sucursales

Body:
{
"nombre": "Sucursal Centro"
}

Actualizar nombre de sucursal
PATCH /franquicias/{franquiciaId}/sucursales/{sucursalId}/nombre

---

## Productos

Agregar producto
POST /franquicias/{franquiciaId}/sucursales/{sucursalId}/productos

Body:
{
"nombre": "Producto A",
"stock": 10
}

Actualizar stock
PUT /franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock

Eliminar producto
DELETE /franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}

Actualizar nombre de producto
PATCH /franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre

---

## Producto con mayor stock por sucursal

GET /franquicias/{franquiciaId}/productos/max-stock-por-sucursal

Respuesta de ejemplo:

[
{
"sucursalId": "1",
"sucursalNombre": "Sucursal Centro",
"productoId": "10",
"productoNombre": "Producto A",
"stock": 50
}
]

Si una sucursal no tiene productos, se devuelve la información de la sucursal con valores nulos.

---

# Buenas Prácticas Aplicadas

- Clean Architecture
- Programación reactiva con WebFlux
- Separación clara de responsabilidades
- Unit testing de casos de uso
- Uso de Docker para infraestructura
- Dominio desacoplado de frameworks

---

# Consideraciones

La aplicación está diseñada para ser completamente reactiva utilizando WebFlux.
La persistencia se realiza en MongoDB mediante Reactive MongoDB.
Los tests unitarios se enfocan en validar la lógica de negocio sin depender de infraestructura externa.
