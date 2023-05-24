# Shared Library de Jenkins

## Introducción
En esta sección puedes incluir una breve descripción de lo que es una shared library de Jenkins y cuál es el propósito de tu shared library en particular.

## Requisitos previos
Aquí puedes listar los requisitos previos necesarios para utilizar tu shared library, como por ejemplo la versión de Jenkins requerida y cualquier otra herramienta o dependencia necesaria.

## Estructura del repositorio
En esta sección puedes explicar la estructura de tu repositorio y el propósito de cada archivo o directorio. Por ejemplo:

```
├── LICENSE
├── README.md
└── vars
    ├── analisysSonarNpm.groovy
    ├── buildArtifactNpm.groovy
    ├── buildNpm.groovy
    ├── cloneRepository.groovy
    ├── dockerBuild.groovy
    ├── dockerDeploy.groovy
    ├── dockerPush.groovy
    ├── hello.groovy
    ├── owaspScan.groovy
    ├── pipelineNodeJs.groovy
    ├── qualityGate.groovy
    └── testNpm.groovy
```
- **vars**: Este directorio contiene los archivos Groovy y txt que definen las funciones y variables globales disponibles en la shared library.
  - **analisysSonarNpm.groovy**: Este archivo define la función `analisysSonarNpm` que ...
  - **buildArtifactNpm.txt**: Este archivo ...
  - **buildNpm.groovy**: Este archivo define la función `buildNpm` que ...
  - ...

## Uso
En esta sección puedes explicar cómo utilizar tu shared library en un pipeline de Jenkins. Puedes incluir ejemplos de código y explicar cómo llamar a las diferentes funciones disponibles.

## Contribución
Si deseas permitir contribuciones a tu shared library, puedes incluir una sección explicando cómo pueden los usuarios contribuir al proyecto.

## Licencia
No olvides incluir información sobre la licencia bajo la cual está disponible tu shared library.
