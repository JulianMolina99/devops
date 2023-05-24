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

### Función `cloneRepository()`

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
    tools {
            nodejs 'NodeJS'
        }
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
```
### Función `buildNpm()`

La función `buildNpm` instala las dependencias y compila el código de un proyecto NPM utilizando los scripts definidos en el archivo `package.json`. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Ejecuta el comando `npm install` para instalar las dependencias del proyecto.
2. Ejecuta el comando `npm run build` para compilar el código del proyecto utilizando el script `build` definido en el archivo `package.json`.

Para utilizar esta función en un pipeline de Jenkins, se debe de tener NPM instalado en el agente donde se ejecutará el pipeline o en su defecto utilizar el plugin de NodeJS para Jenkins, el codigo de la aplicacion y de tener un script `build` válido en el archivo `package.json` del proyecto.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    tools {
            nodejs 'NodeJS'
        }
    stages {
    
        stage('Clonar repositorio') {
            steps {
                script {
                    cloneRepository(scmUrl: 'https://github.com/mi-usuario/mi-repositorio.git')
                }
            }
        }
        
        stage('Compilar código') {
            steps {
                script {
                    buildNpm()
                }
            }
        }
    }
}
```

### Función `testNpm()`

La función `call` ejecuta las pruebas de un proyecto NPM utilizando los scripts definidos en el archivo `package.json`. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Ejecuta el comando `npm test` para ejecutar las pruebas del proyecto utilizando el script `test` definido en el archivo `package.json`, se recomienda generar reportes de cobertura de codigo y reporte de las pruebas unitarias del proyecto al utilizar esta funcion.

```
"test": "jest --coverage --testResultsProcessor=jest-sonar-reporter"
```

Para utilizar esta función en un pipeline de Jenkins, se debe de tener NPM instalado en el agente donde se ejecutará el pipeline o en su defecto utilizar el plugin de NodeJS para Jenkins, el codigo de la aplicacion y de tener un script `test` válido como en el ejemplo anterior en el archivo `package.json` del proyecto.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    tools {
            nodejs 'NodeJS'
        }
    stages {
    
        stage('Clonar repositorio') {
            steps {
                script {
                    cloneRepository(scmUrl: 'https://github.com/mi-usuario/mi-repositorio.git')
                }
            }
        }
        
        stage('Ejecutar pruebas') {
            steps {
                script {
                    testNpm()
                }
            }
        }
    }
}
```

### Función `buildArtifactNpm()`

La función `buildArtifactNpm` crea un archivo tar.gz del directorio `dist` y lo archiva como un artefacto en Jenkins. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Ejecuta el comando `tar -czvf nodejs_app.tar.gz dist` para crear un archivo tar.gz del directorio `dist`.
2. Utiliza la función `archiveArtifacts` de Jenkins para archivar el archivo tar.gz como un artefacto en Jenkins y habilitar el seguimiento de huellas dactilares.

Para utilizar esta función en un pipeline de Jenkins, se debe tener el comando `tar` disponible en el agente donde se ejecutará el pipeline y de tener un directorio `dist` válido en el proyecto, este directorio `dist` se genera al momento de realizar el `build` de la aplicacion.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Archivar artefacto') {
            steps {
                script {
                    buildArtifactNpm()
                }
            }
        }
    }
}
```


## Uso
En esta sección puedes explicar cómo utilizar tu shared library en un pipeline de Jenkins. Puedes incluir ejemplos de código y explicar cómo llamar a las diferentes funciones disponibles.

## Contribución
Si deseas permitir contribuciones a tu shared library, puedes incluir una sección explicando cómo pueden los usuarios contribuir al proyecto.

## Licencia
No olvides incluir información sobre la licencia bajo la cual está disponible tu shared library.
