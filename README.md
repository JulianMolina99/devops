# Shared Library de Jenkins

## Introducción
Una Shared Library en Jenkins es una forma de encapsular y reutilizar código común en pipelines. Proporciona una manera de extender las capacidades de Jenkins y promover las mejores prácticas en la implementación de pipelines de CI/CD. Esta documentación describe una Shared Library específica para realizar el pipeline en Jenkins de una aplicacion de NodeJs, esta aplicacion se puede encontrar en el siguiente repositorio: https://github.com/JulianMolina99/NodeJSApp.git

## Requisitos previos
- Instalar Docker:
 - [Docker](https://docs.docker.com/desktop/install/windows-install/) para Windows, en este caso también se instala la herramienta de docker-compose
 - [Docker](https://docs.docker.com/engine/install/ubuntu/) para Ubuntu
 - [Docker-compose](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-ubuntu-20-04) para Ubuntu
- [Jenkins](https://www.jenkins.io/doc/book/installing/docker/) versión 2.387.3 o superior
- [SonarQube](https://hub.docker.com/_/sonarqube)


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

La función `testNpm` ejecuta las pruebas de un proyecto NPM utilizando los scripts definidos en el archivo `package.json`. Esta función no toma parámetros.

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

### Función `analisysSonarNpm()`
La función `analisysSonarNpm` ejecuta un análisis de SonarQube en el repositorio actual. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Define una variable `scannerTool` que contiene la herramienta 'SonarQube Scanner'.
2. Define una variable `repoName` que contiene el nombre del repositorio actual en minúsculas.
3. Si la herramienta 'SonarQube Scanner' está disponible, se ejecuta un análisis de SonarQube en el repositorio actual utilizando la herramienta 'SonarQube Scanner' y la configuración del servidor 'SonarQube Local Server'.
4. El comando `sh` ejecuta el script de SonarQube Scanner con los siguientes parámetros:
    - `-Dsonar.projectKey='${repoName}'`: Establece la clave del proyecto en SonarQube como el nombre del repositorio actual.
    - `-Dsonar.projectName='${repoName}'`: Establece el nombre del proyecto en SonarQube como el nombre del repositorio actual.
    - `-Dsonar.sources=src`: Establece el directorio de fuentes a analizar como `src`.
    - `-Dsonar.tests=src/__test__`: Establece el directorio de pruebas a analizar como `src/__test__`.
    - `-Dsonar.exclusions=src/__test__/**`: Excluye todos los archivos en el directorio `src/__test__` del análisis.
    - `-Dsonar.testExecutionReportPaths=./test-report.xml`: Establece la ruta del informe de ejecución de pruebas como `./test-report.xml`.
    - `-Dsonar.javascript.lcov.reportPaths=coverage/lcov.info`: Establece la ruta del informe de cobertura de código para JavaScript como `coverage/lcov.info`.
5. Si la herramienta 'SonarQube Scanner' no está disponible, se muestra un error.

Para utilizar esta función en un pipeline de Jenkins, se debe tener la herramienta 'SonarQube Scanner' y el servidor 'SonarQube Local Server' configurados en Jenkins.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Análisis SonarQube') {
            steps {
                script {
                    analisysSonarNpm()
                }
            }
        }
    }
}
```

### Función `qualityGate()`
La función `qualityGate` espera a que se complete el análisis de calidad de SonarQube y aborta el pipeline si el análisis falla. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Utiliza la función `timeout` de Jenkins para establecer un tiempo límite de 1 hora para esperar a que se complete el análisis de calidad de SonarQube.
2. Utiliza la función `waitForQualityGate` de Jenkins para esperar a que se complete el análisis de calidad de SonarQube.
3. Si el análisis de calidad de SonarQube falla, se aborta el pipeline.

Para utilizar esta función en un pipeline de Jenkins, se debe tener un análisis de calidad de SonarQube configurado en el pipeline.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Esperar análisis de calidad') {
            steps {
                script {
                    qualityGate()
                }
            }
        }
    }
}
```

### Función `dockerBuild()`
La función `dockerBuild` construye una imagen de Docker para el repositorio actual. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Define una variable `repoName` que contiene el nombre del repositorio actual en minúsculas.
2. Utiliza la función `docker.build` de Jenkins para construir una imagen de Docker para el repositorio actual utilizando el nombre de la imagen como `julianmol007/${repoName}:${env.BUILD_ID}`.

Para utilizar esta función en un pipeline de Jenkins, se debe tener Docker instalado y configurado en el agente donde se ejecutará el pipeline.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Construir imagen de Docker') {
            steps {
                script {
                    dockerBuild()
                }
            }
        }
    }
}
```

### Función `dockerPush()`
La función `dockerPush` carga una imagen de Docker para el repositorio actual en Docker Hub. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Define una variable `repoName` que contiene el nombre del repositorio actual en minúsculas.
2. Utiliza la función `docker.image` de Jenkins para obtener una imagen de Docker para el repositorio actual utilizando el nombre de la imagen como `julianmol007/${repoName}:${env.BUILD_ID}`.
3. Utiliza la función `docker.withRegistry` de Jenkins para iniciar sesión en Docker Hub utilizando las credenciales almacenadas en Jenkins con el ID 'docker-login'.
4. Utiliza la función `push` del objeto `dockerImage` para cargar la imagen de Docker en Docker Hub con la etiqueta `${env.BUILD_ID}`.

Para utilizar esta función en un pipeline de Jenkins, se debe tener Docker instalado y configurado en el agente donde se ejecutará el pipeline y tener las credenciales de Docker Hub almacenadas en Jenkins con el ID 'docker-login'.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Cargar imagen de Docker') {
            steps {
                script {
                    dockerPush()
                }
            }
        }
    }
}
```

### Función `dockerDeploy()`
La función `dockerDeploy` inicia un conjunto de servicios definidos en un archivo `docker-compose.yml` en el repositorio actual. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Define una variable `repoName` que contiene el nombre del repositorio actual en minúsculas.
2. Utiliza la función `withEnv` de Jenkins para establecer las variables de entorno `repoName` y `BUILD_ID` con los valores del nombre del repositorio y el ID de la compilación actual, respectivamente.
3. Ejecuta el comando `docker-compose up -d` para iniciar los servicios definidos en el archivo `docker-compose.yml`.

Para utilizar esta función en un pipeline de Jenkins, se debe tener Docker Compose instalado y configurado en el agente donde se ejecutará el pipeline y tener un archivo `docker-compose.yml` válido en el repositorio.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Iniciar servicios') {
            steps {
                script {
                    dockerDeploy()
                }
            }
        }
    }
}
```

### Función `owaspScan()`
La función `owaspScan` ejecuta un análisis de seguridad OWASP ZAP en una aplicación Node.js. Esta función no toma parámetros.

El código de esta función hace lo siguiente:
1. Utiliza la función `docker.image` de Jenkins para obtener una imagen de Docker para OWASP ZAP utilizando el nombre de la imagen como `owasp/zap2docker-stable`.
2. Utiliza la función `withRun` del objeto `owaspImage` para iniciar un contenedor de Docker con la imagen de OWASP ZAP y ejecutar los siguientes comandos:
    - Ejecuta el script `zap-baseline.py` en el contenedor de Docker para realizar un análisis básico de seguridad en la aplicación Node.js en la URL `http://nodejs_app:3001` y guardar el informe en el archivo `report_baseInline.html`.
    - Ejecuta el script `zap-full-scan.py` en el contenedor de Docker para realizar un análisis completo de seguridad en la aplicación Node.js en la URL `http://nodejs_app:3001` y guardar el informe en el archivo `report_fullScan.html`.
    - Copia los archivos `report_baseInline.html` y `report_fullScan.html` desde el contenedor de Docker al directorio de trabajo actual.
    - Copia los archivos `report_baseInline.html` y `report_fullScan.html` desde el directorio de trabajo actual al directorio del trabajo actual en Jenkins.

Para utilizar esta función en un pipeline de Jenkins, se debe tener Docker instalado y configurado en el agente donde se ejecutará el pipeline y tener una aplicación Node.js válida ejecutándose en la URL `http://nodejs_app:3001`.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Análisis de seguridad OWASP ZAP') {
            steps {
                script {
                    owaspScan()
                }
            }
        }
    }
}
```
### Función `pipelineNodeJs()`
La función `pipelineNodeJs` define un pipeline de Jenkins para construir, probar y desplegar una aplicación Node.js. Esta función toma un parámetro `parameters` que contiene los parámetros del pipeline.

El código de esta función hace lo siguiente:
1. Define un agente para ejecutar el pipeline en cualquier nodo disponible.
2. Define una herramienta `nodejs` con el nombre 'NodeJS' para utilizar en el pipeline.
3. Define varias etapas en el pipeline:
    - Etapa 'Checkout': Clona el repositorio utilizando la función `cloneRepository` y los parámetros especificados en `parameters`.
    - Etapa 'Build app': Construye la aplicación Node.js utilizando la función `buildNpm`.
    - Etapa 'Test app': Prueba la aplicación Node.js utilizando la función `testNpm`.
    - Etapa 'Build artifact': Construye un artefacto de la aplicación Node.js utilizando la función `buildArtifactNpm`.
    - Etapa 'Analisys with sonar': Realiza un análisis de SonarQube en la aplicación Node.js utilizando la función `analisysSonarNpm`.
    - Etapa 'Quality Gate': Espera a que se complete el análisis de calidad de SonarQube y aborta el pipeline si el análisis falla utilizando la función `qualityGate`.
    - Etapa 'Deploy and Analisys in Develop': Esta etapa solo se ejecuta si la rama actual es `origin/develop` o `origin/master`. Contiene varias sub-etapas:
        - Sub-etapa 'Build image Docker': Construye una imagen de Docker para la aplicación Node.js utilizando la función `dockerBuild`.
        - Sub-etapa 'Push Docker Image': Carga la imagen de Docker en Docker Hub utilizando la función `dockerPush`.
        - Sub-etapa 'Deploy App with Docker': Despliega la aplicación Node.js utilizando Docker y la función `dockerDeploy`.
        - Sub-etapa 'Analisys With OWASP ZAP': Realiza un análisis de seguridad OWASP ZAP en la aplicación Node.js utilizando la función `owaspScan`.

Para utilizar esta función en un pipeline de Jenkins, se debe tener Node.js, Docker y SonarQube instalados y configurados en el agente donde se ejecutará el pipeline.

Para utilizar esta función en un pipeline de Jenkins, se puede hacer de la siguiente manera:

```groovy
@Library('my-shared-library') _

pipelineNodeJs(parameters)
```

## Uso

Para configurar la Shared Library en Jenkins, siga los pasos detallados a continuación:

1. **Acceda a la configuración del sistema de Jenkins**: Ingrese a la interfaz de Jenkins y seleccione "Manage Jenkins" en el menú lateral izquierdo, a continuación seleccione "Configure System".

2. **Configure la Shared Library**: Diríjase hacia abajo hasta llegar a la sección "Global Pipeline Libraries". Haga clic en "Add" para agregar una nueva Shared Library. Complete los campos con la siguiente información:

    - **Name**: Este será el nombre que se utilizará para referenciar la Shared Library en los pipelines. Por ejemplo, puede usar `my-shared-library`.
  
    - **Default version**: Aquí puede especificar la rama por defecto del repositorio que Jenkins debería utilizar, por ejemplo `main` o dejar este espacio en blanco.
  
    - **Retrieval method**: Seleccione "Modern SCM" y luego seleccione "GitHub" (o el tipo de repositorio que esté utilizando).

    - **Source Code Management**: Proporcione el gestor de codigo fuente en este caso es Git.

    - **Project Repository**: Introduzca la URL del repositorio de GitHub en el que se encuentra la Shared Library.

    - **Credentials**: Si el repositorio es privado, necesitará proporcionar las credenciales de GitHub correspondientes. Si es público, puede dejar esta opción en "- none -".

3. **Guarde los cambios**: Diríjase hasta la parte inferior de la página y haga clic en "Save" para guardar los cambios.

Una vez completados los pasos anteriores, la Shared Library está lista para ser utilizada en los pipelines. Simplemente se debe agregar al inicio del archivo `Jenkinsfile` con la siguiente sintaxis:

\```groovy
@Library('my-shared-library') _
\```

Asegúrese de reemplazar `my-shared-library` con el nombre que ha usado al configurar la Shared Library.

De esta manera, todas las funciones definidas en la Shared Library estarán disponibles para su uso en el pipeline. Recuerde revisar la documentación de cada función para entender cómo utilizarla correctamente.



## Licencia
Este proyecto está bajo la Licencia MIT - revisar el archivo LICENSE.md para mas detalles
