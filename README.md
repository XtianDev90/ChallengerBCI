# GlobalLogic Ejercicio JAVA

_Ejercicio BCI para registrar un usuario, obtener un id de tipo UUDI y obtener un usuario por su codigo UUDI_

## Comenzando 

_Descargar proyecto desde repositorio_

```
$ git clone https://github.com/XtianDev90/globalLogic.git
$ cd globalLogic/
$ cd globalLogic/
```
_Luego para compilar desde una ventana de comando en la carpeta del proyecto_

```
$ gradle build
```

_Arrancamos la aplicacion en la carpeta que se genera el Jar_

```
$ cd build/libs
$ java -jar .\globalLogic-0.0.1-SNAPSHOT.jar
```

### Pre-requisitos 

_Programas y versiones_

```
Postman
Java 8
Gradle 6.8
Spring Tool Suite 4
H2
Lombok
```

### Ejecucion 

_En la carpeta Documentos se encuentra la coleccion para las pruebas de la API_

```
globalLogic.postman_collection
```

_La coleccion tiene los request para "sign-up" y "login", ademas de 2 endpoint de prueba "hello" y "users"_
_Tambien se puede ejecutar en una ventana de comando_

```
curl -X POST -H "Content-Type:application/json" -d "{\"name\":\"Admin\",\"email\":\"Prueba@Dominio.cl\",\"password\":\"a2asfGfdfdf4\",\"phones\": [{\"number\":974524787,\"citycode\":11,\"contrycode\":\"13\"}]}" "http://localhost:8080/api/v1/sign-up"
```
_Resultado_

```
{"idUser":"fd2b4d1f-699c-4755-801a-7366021968d4","name":"Admin","email":"Prueba@Dominio.cl","password":"$2a$10$b69TVKaM70VoWzOolN7RpOWaHyFe6U2PtAhbj9T/xUemZ1VHLPgOK","phones":[{"idPhone":2,"number":974524787,"citycode":11,"contrycode":"13"},{"idPhone":1,"number":974524787,"citycode":11,"contrycode":"13"}],"created":"2022-02-08T12:51:03.283","lastLogin":"2022-02-08T12:50:56.801","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQcnVlYmFARG9taW5pby5jbCIsImV4cCI6MTY0NDMzOTA1NX0.6fNXv2km3-fu7MLrX8BVvpIOl1LyUdm_6zShhVmgkTw","active":true}
```

_Despues de crear el registro y el token, se puede consultar con el UUDI que retorno "idUser":"fd2b4d1f-699c-4755-801a-7366021968d4" y con el token de autorizacion "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQcnVlYmFARG9taW5pby5jbCIsImV4cCI6MTY0NDMzOTA1NX0.6fNXv2km3-fu7MLrX8BVvpIOl1LyUdm_6zShhVmgkTw"_
```
curl -X GET -H "Content-Type:application/json" --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQcnVlYmFARG9taW5pby5jbCIsImV4cCI6MTY0NDMzOTA1NX0.6fNXv2km3-fu7MLrX8BVvpIOl1LyUdm_6zShhVmgkTw" "http://localhost:8080/api/v1/login/fd2b4d1f-699c-4755-801a-7366021968d4"
```
_Resultado_

```
{"idUser":"fd2b4d1f-699c-4755-801a-7366021968d4","name":"Admin","email":"Prueba@Dominio.cl","password":"$2a$10$b69TVKaM70VoWzOolN7RpOWaHyFe6U2PtAhbj9T/xUemZ1VHLPgOK","phones":[{"idPhone":2,"number":974524787,"citycode":11,"contrycode":"13"}],"created":"2022-02-08T12:51:03.283","lastLogin":"2022-02-08T13:06:01.259","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQcnVlYmFARG9taW5pby5jbCIsImV4cCI6MTY0NDMzOTA1NX0.6fNXv2km3-fu7MLrX8BVvpIOl1LyUdm_6zShhVmgkTw","active":true}
```

## Test Unitarios
_En una ventana de comando en la ruta del proyecto, ejecutar el siguiente comando:_
```
$ gradle clean build jacocoTestReport 
```
_Luego ir a la ruta: build\reports\jacoco\test\html y abrir archivo index_


