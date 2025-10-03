# TheBoys-Backend

Integrantes:

- Juan Sebastian Puentes Julio
- Tulio Riaño
- Nestor Lopez
- Daniel Patiño
- Julian Camilo Lopez

---

## 🌲Ramificacion y Estrategia de Versionamiento de ramas

- Se realiza la respectiva ramificacion creando las ramas `develop` y `feature` para desarrollar las nuevas funcionalidades de la aplicacion. Para los commits manejamos el estandar `feat/modulo: Accion Realizada - Nombre Quien Lo Hizo` para que se entienda con claridad lo elaborado.

---

## 💻 **Como ejecutar el proyecto**

- Clonar El Repositorio: `https://github.com/tulio3101/TheBoys-Backend.git`
- Desde el cmd, ejecutamos: `cd TheBoys-Backend`
- Allí, con ayuda de maven, ejecutamos: `mvn clean compile`
- Una vez compile, ejecutamos: `mvn spring-boot:run`
- Para las pruebas, ejecutamos el comando: `mvn test`

---

## 1. 🎨 **Diseño**:

_Diagrama De Contexto_

- Se considera el sistema de manejo de solicitudes SIRHA donde administradores, decanos y estudiantes podrán realizar peticiones y responder a las mismas:

![alt text](docs/imagenes/contexto.png)

---

_Diagrama de Clases:_

- Se realizó la diagramacion del problema a resolver aplicando patrones de diseño y siguiendo los principios SOLID:

![alt text](docs/imagenes/clasesCORREGIDO.png)

---

_Diagrama De Casos De Uso:_

![alt text](docs/imagenes/bmbRDnJdKAkAAAAASUVORK5CYII.png)

---

_Diagrama De Componentes General:_

![alt text](docs/imagenes/generalCORREGIDO.png)

---

_Diagrama De Componentes Especifico:_

![alt text](docs/imagenes/DiagramaComponentesEspecifico.png)

---

_Diagramas De Secuencia:_

- Se realizó el diseño de la mayoria de funciones que brinda el sistema SIRHA.

![alt text](docs/imagenes/secuencia1.png)

![alt text](docs/imagenes/secuencia2.png)

![alt text](docs/imagenes/secuencia3.png)

![alt text](docs/imagenes/secuencia4.png)

![alt text](docs/imagenes/secuencia5.png)

---

_Diagrama De Bases De Datos:_

![alt text](docs/imagenes/AZgfNLy8nCBpAAAAAElFTkSuQmCC.png)

_Diagrama De Bases De Datos Corregido:_

![alt text](docs/imagenes/baseDeDatos.png)

---
_Diagrama de Despliegue:_
![alt text](docs/imagenes/DiagramaDeDespliegue.png)

## 2. 🔥 **Jacoco**

## ![alt text](docs/imagenes/jacocoCORREGIDO.png)

---

## 3. 🔥 **Swagger**

![alt text](docs/imagenes/swaggeer.png)

![alt text](docs/imagenes/SWAGGER1.png)

![alt text](docs/imagenes/SWAGGER2.png)

![alt text](docs/imagenes/SWAGGER3.png)

---

## 4. 🔥 **MongoDB**

Base de datos desplegada en MongoDB Atlas:

![alt text](docs/imagenes/MongoCompass.png)

Ejemplo de documento en la coleccion de materias:
![alt text](docs/imagenes/Subjects.png)

---

## 5. 🔥 **SonarQube**

Analisis estatico ejecutado con SonarQube:

![alt text](docs/imagenes/AnalisisSonarQube.png)

---

## 6. 🔥 **Docker**

![alt text](docs/imagenes/docker.png)
