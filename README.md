# Shared Library de Jenkins

## Introducción
Una Shared Library en Jenkins es una forma de encapsular y reutilizar código común en pipelines. Proporciona una manera de extender las capacidades de Jenkins y promover las mejores prácticas en la implementación de pipelines de CI/CD. Esta documentación describe una Shared Library específica para realizar el pipeline en Jenkins de una aplicacion de NodeJs.

## Requisitos previos
- Jenkins versión 2.387.3 o superior
- Docker
- SonarQube


## Estructura del repositorio
La estructura de esta Shared Library de Jenkins se explica a continuación:

- **vars**: Este directorio contiene los archivos Groovy que definen las funciones disponibles en la shared library.
    - **analisysSonarNpm.groovy**: Este archivo define la función `analisysSonarNpm` que realiza el análisis estático del código fuente utilizando SonarQube.
    - **buildArtifactNpm.groovy**: Este archivo define la función `buildArtifactNpm` que genera un artefacto del proyecto utilizando NPM.
    - **buildNpm.groovy**: Este archivo define la función `buildNpm` que compila el código y maneja las dependencias utilizando NPM.
    - **cloneRepository.groovy**: Este archivo define la función `cloneRepository` que clona el repositorio del proyecto.
    - **dockerBuild.groovy**: Este archivo define la función `dockerBuild` que construye una imagen Docker del proyecto.
    - **dockerDeploy.groovy**: Este archivo define la función `dockerDeploy` que despliega la aplicación utilizando Docker compose.
    - **dockerPush.groovy**: Este archivo define la función `dockerPush` que sube la imagen Docker de la aplicacion a Dockerhub.
    - **owaspScan.groovy**: Este archivo define la función `owaspScan` que realiza un análisis de seguridad de la aplicacion utilizando OWASP ZAP.
    - **qualityGate.groovy**: Este archivo define la función `qualityGate` que verifica los criterios de calidad del código y aborta el pipeline en caso de que el codigo no pase el Quality Gate de SonarQube.
    - **testNpm.groovy**: Este archivo define la función `testNpm` que ejecuta las pruebas en el proyecto utilizando NPM.
    - **pipelineNodeJs.groovy**: Este archivo define el pipeline de la aplicacion de NodeJs y utiliza todas las funciones mencionadas anteriormente en los stages.

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


## Uso
En esta sección puedes explicar cómo utilizar tu shared library en un pipeline de Jenkins. Puedes incluir ejemplos de código y explicar cómo llamar a las diferentes funciones disponibles.

## Contribución
Si deseas permitir contribuciones a tu shared library, puedes incluir una sección explicando cómo pueden los usuarios contribuir al proyecto.

## Licencia
No olvides incluir información sobre la licencia bajo la cual está disponible tu shared library.
