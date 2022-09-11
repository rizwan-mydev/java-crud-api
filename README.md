## Java CRUD Application
CRUD application built with Java Spring Boot, an N-Tier architecture, REST Endpoints, a full unit test suite using h2, GitHub actions CI, and Docker support.

This project is made of 3 separate Docker containers that hold:

- PostgreSQL database (localhost:5432)
- pgAdmin4 database tool (localhost:5050)
- Java Spring Boot backend (localhost:8080)
---

### Prerequisites

In order to run this application you need to install two tools: **Docker** & **Docker Compose**.

Instructions how to install **Docker** on [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/)

**Dosker Compose** is already included in installation packs for *Windows*, so only Ubuntu users need to follow [these instructions](https://docs.docker.com/compose/install/).

The latest docker image can be found at https://hub.docker.com/r/clh7090/movieapp/

---


### How to run it?

the entire application can be run with a single command in a terminal:

```
$ docker-compose up -d
```

If you want to stop the application use following command:

```
$ docker-compose down
```
---
