# PruebaAgendaPro

Este proyecto es una aplicación que maneja una lista de productos utilizando una arquitectura
modular en Kotlin con Spring Boot.

## Requisitos

- Docker
- Docker Compose

## Ejecución

### 1. Construcción y ejecución del Dockerfile

Para construir y ejecutar la imagen Docker de la aplicación, sigue los siguientes pasos:

1. Navega al directorio donde se encuentra el proyecto y esta alojado el archivo `Dockerfile`.
2. Construye la imagen Docker con el siguiente comando:

    ```sh
    docker build -t agenda_pro_products .
    ```

### 2. Ejecución con Docker Compose

luego de construir la imagen de debe ejecutar el comando de docker compose:

1. Asegúrate estar en el mismo directorio donde esta el proyecto ya que ahi esta el
   archivo `docker-compose.yml`.
2. Ejecuta el siguiente comando para iniciar los servicios definidos en el
   archivo `docker-compose.yml`:

    ```sh
    docker-compose up -d
    ```

Esto levantará todos los servicios definidos en el archivo `docker-compose.yml`, incluyendo la base
de datos PostgreSQL y el servicio de la aplicación.

## Información Adicional

- El proyecto utiliza PostgreSQL para guardar la información de los productos.
- La información de las categorías se guarda de manera volátil en memoria, por lo que se perderá al
  reiniciar el servicio.
- Existen dos usuarios creados con hardcode: `admin` y `user`, donde la contraseña para la
  autenticación básica es el mismo nombre de usuario.
- Solo el usuario `admin` puede acceder a la API de categorías.

## APIs

### Productos

#### Crear un producto

- **URL**: `/v1/products`
- **Método**: `POST`
- **Descripción**: Crea un nuevo producto.
- **Ejemplo de `curl`**:

    ```sh
    curl --location 'http://localhost:8080/v1/products' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Basic YWRtaW46YWRtaW4=' \
    --data '{
        "sku": "SKU-00001",
        "name": "Producto Prueba",
        "price": 10000.0,
        "category": "Categoria prueba"
    }'
    ```

#### Actualizar un producto

- **URL**: `/v1/products/{id}`
- **Método**: `PATCH`
- **Descripción**: Actualiza un producto existente.
- **Ejemplo de `curl`**:

    ```sh
    curl --location --request PATCH 'http://localhost:8080/v1/products/2fb5e83b-139f-4b2d-a95e-30ea3077d386' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Basic YWRtaW46YWRtaW4=' \
    --data '{
        "sku": "SKU-00002",
        "name": "Producto Prueba 2",
        "price": 20000.0,
        "category": "Categoria prueba"
    }'
    ```

#### Borrar un producto

- **URL**: `/v1/products/{id}`
- **Método**: `DELETE`
- **Descripción**: Borra un producto existente.
- **Ejemplo de `curl`**:

    ```sh
    curl --location --request DELETE 'http://localhost:8080/v1/products/91ca5f33-1156-4bdf-b19b-dd92a92f6fdd' \
    --header 'Authorization: Basic YWRtaW46YWRtaW4='
    ```

#### Buscar todos los productos

- **URL**: `/v1/products`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todos los productos con paginación y filtrado.
- **Parámetros**:
    - `page`: Número de la página (por defecto es 0).
    - `size`: Tamaño de la página (por defecto es 10).
    - `search`: Parámetros de búsqueda (por ejemplo, `sku=like=2` esto obtendra los productos que en
      su SKU contengan un 2, `name=="Producto Prueba 2"` me recuperara de la base de datos solo el
      producto con ese nombre).
- **Ejemplo de `curl`**:

    ```sh
    curl --location 'http://localhost:8080/v1/products?page=0&size=2&search=sku%3Dlike%3D2' \
    --header 'Cookie: JSESSIONID=30290C4397FCCB5E58AB4AFF84A2A1F3'
    ```

#### Buscar un producto por ID

- **URL**: `/v1/products/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene un producto por su ID.
- **Ejemplo de `curl`**:

    ```sh
    curl --location 'http://localhost:8080/v1/products/2fb5e83b-139f-4b2d-a95e-30ea3077d386' \
    --header 'Cookie: JSESSIONID=30290C4397FCCB5E58AB4AFF84A2A1F3'
    ```

### Categorías

#### Ver productos por categoría

- **URL**: `/v1/category/{nombreCategoria}`
- **Método**: `GET`
- **Descripción**: Obtiene los productos asociados a una categoría.
- **Ejemplo de `curl`**:

    ```sh
    curl --location 'http://localhost:8080/v1/category/Categoria prueba' \
    --header 'Authorization: Basic YWRtaW46YWRtaW4=' \
    --header 'Cookie: JSESSIONID=30290C4397FCCB5E58AB4AFF84A2A1F3'
    ```

> **Nota**: Toda la API de productos está disponible para los usuarios `user` y `admin`. Los métodos
> GET no necesitan autenticación. Solo el usuario `admin` puede ver la API de categorías.
