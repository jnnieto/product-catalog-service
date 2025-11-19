# 🛒 Product Service

Microservicio de gestión de productos — parte del ecosistema StockFlow (o el nombre general del proyecto que utilices).

## 📌 Descripción

El **Product Service** es un microservicio desarrollado en **Spring Boot** encargado de la administración del catálogo de productos.  
Permite crear, consultar y listar productos dentro del sistema, y sirve como base para otros módulos como inventarios, ventas o pedidos.

---

## 📦 Modelo: Producto

El microservicio gestiona la entidad **Producto**, que incluye:

| Campo        | Tipo   | Descripción                          |
|--------------|--------|--------------------------------------|
| `id`         | Long   | Identificador único del producto     |
| `nombre`     | String | Nombre del producto                  |
| `precio`     | Double | Precio del producto                  |
| `descripcion`| String | (Opcional) Descripción detallada     |

---

## 🚀 Funcionalidades

El Product Service expone las siguientes operaciones:

### ✅ 1. Crear un nuevo producto
- **Método:** `POST`
- **Endpoint:** `/products`
- **Descripción:** Registra un nuevo producto en el sistema.

### ✅ 2. Obtener un producto por ID
- **Método:** `GET`
- **Endpoint:** `/products/{id}`
- **Descripción:** Devuelve la información de un producto específico.

### ✅ 3. Listar todos los productos (opcional)
- **Método:** `GET`
- **Endpoint:** `/products`
- **Descripción:** Obtiene el catálogo completo de productos disponibles.

---

## 🧱 Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 / MySQL / PostgreSQL (según configuración)
- Maven

---

## 📂 Estructura del proyecto

