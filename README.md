# Challenge Search Tracker

Aplicacion Spring Boot 3.5.13 y Java 21 que permite realizar busquedas por hotel, publicar las busquedas en un topico de kafka, para que luego puedan ser persistidas en una base de datos y consultar cuantas veces se repitio una busqueda exacta.

## Stack

- Java 21
- Spring Boot 3.5.13
- Spring Web, Validation, Spring Kafka y Spring Data JPA
- Oracle como base de datos
- Kafka para mensajeria de eventos
- Swagger
- Jacoco con umbral minimo de 80% en lineas, branches, sentencias y metodos

## Arquitectura

Se implemento arquitectura hexagonal:

- `domain`: modelo, excepciones del dominio y puertos de salida
- `application`: casos de uso y puertos de entrada
- `infrastructure.adapter.in`: REST y consumidor Kafka
- `infrastructure.adapter.out`: productor Kafka, generador de ID y persistencia
- `infrastructure.config`: configuracion universal

## Endpoints

### `POST /search`

Request:

```json
{
  "hotelId": "1234aBc",
  "checkIn": "29/12/2023",
  "checkOut": "31/12/2023",
  "ages": [30, 29, 1, 3]
}
```

Response:

```json
{
  "searchId": "xxxxx"
}
```

### `GET /count?searchId=xxxxx`

Response:

```json
{
  "searchId": "xxxxx",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [30, 29, 1, 3]
  },
  "count": 1
}
```

## Interfaces visuales

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) Documentacion de la API
- Kafka UI: [http://localhost:8081](http://localhost:8081) Se puede usar para ver los mensajes en Kafka
- CloudBeaver: [http://localhost:8978](http://localhost:8978) Se puede usar para consultar la base de datos Oracle

## Tests y coverage

Para ejecutar tests y verificar Jacoco localmente:

```bash
./mvnw verify
```
Ver reporte en `target/site/jacoco/index.html`

## Levantar todo con Docker Compose

Requisito: Tener Docker y Docker Compose disponibles.

1. Construir y levantar la solucion completa:

```bash
docker compose up --build
```

Apagar contenedores:

```bash
docker compose down
```

## Como correr desde el IDE

1. Para ejecutar la aplicacion desde IntelliJ o otro IDE, primero levantar las dependecias con docker comprose de kafka y oracle-db

```bash
docker compose up -d kafka oracle-db
```

2. Luego correr la aplicacion de SpringBoot usando el profile `local`.