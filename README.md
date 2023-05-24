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

## Documentación de funciones

### Función `cloneRepository`

La función `cloneRepository` clona un repositorio Git utilizando las credenciales especificadas. Esta función toma un parámetro de tipo Map que debe contener la siguiente clave:
- `scmUrl`: La URL del repositorio Git a clonar.

El código de esta función hace lo siguiente:
1. Utiliza la función `git` de Jenkins para clonar el repositorio especificado en el parámetro `scmUrl`.
2. Utiliza las credenciales con el ID `token_github` para autenticarse en el repositorio.

Para utilizar esta función en un pipeline de Jenkins, se debe tener configuradas las credenciales o token de GitHub con el ID `token_github` en la configuración global de credenciales de Jenkins.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Clonar repositorio') {
            steps {
                script {
                    cloneRepository(scmUrl: 'https://github.com/mi-usuario/mi-repositorio.git')
                }
            }
        }
    }
}


## Uso
En esta sección puedes explicar cómo utilizar tu shared library en un pipeline de Jenkins. Puedes incluir ejemplos de código y explicar cómo llamar a las diferentes funciones disponibles.

## Contribución
Si deseas permitir contribuciones a tu shared library, puedes incluir una sección explicando cómo pueden los usuarios contribuir al proyecto.

## Licencia
No olvides incluir información sobre la licencia bajo la cual está disponible tu shared library.
