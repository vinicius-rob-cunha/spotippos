# Spotippos

Projeto DEMO para desenvolver uma aplicação SPA com Angular com servidor de autenticação
e consumindo uma API RESTful, ambos servidores com Spring Boot, OAuth2 e JWT

# Descrição / Objetivo

O objetivo é usuário acessar a aplicação angular e caso não esteja autentica redirecionar para o 
servidor de autenticação.

O servidor de autenticação por sua vez irá exibir a página de login e após a autenticação retornar para a aplicação que chamou informando o token jwt

A aplicação origem armazena esse token e realiza as chamadas para a API RESTful através dele

# OAuth 2.0

## Realizando autenticação e recuperando o token

Enteder os parâmetros de resposta

1. Redireciona para o servidor de autenticação

   **Request**

   GET http://localhost:9999/oauth/authorize?response_type=code&client_id=web_app&redirect_uri=http://example.com&state=1234zyx
   *Parâmentro state opcional com uma string aleatória que será verificada depois*

   **Response**

   http://example.com/?code=5tefj0&state=1234zyx

2. Após receber a resposta faz um post para o TOKEN com grant_type=authorization_code e o code recebido

  **Request**

  POST http://localhost:9999/oauth/token?grant_type=authorization_code&client_id=web_app&redirect_uri=http://example.com&code=5tefj0

  **Response**

  ```json
  {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3BvdGlwcG9zLWFwaSJdLCJ1c2VyX25hbWUiOiJ2aW5pY2l1cyIsInNjb3BlIjpbInJlYWQiXSwidXNlcl9kYXRhIjp7ImlkIjoxLCJ1c2VybmFtZSI6InZpbmljaXVzIiwiZW1haWwiOiJ2aW5pY2l1c0B0ZXN0ZS5jb20ifSwiZXhwIjoxNTE0MjUyNjE3LCJhdXRob3JpdGllcyI6WyJST0xFX1VTVUFSSU9fQ09NVU0iLCJyZWFkIl0sImp0aSI6ImZiNDk3YTRhLTk5YjYtNDAyZC04OWY3LTVjNmVhZWFkOTdhNSIsImNsaWVudF9pZCI6IndlYl9hcHAifQ.SeHm7Ecjzuz9Khhcg-E9AjnDuGFke1WnDF_h2wW62KQ",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3BvdGlwcG9zLWFwaSJdLCJ1c2VyX25hbWUiOiJ2aW5pY2l1cyIsInNjb3BlIjpbInJlYWQiXSwiYXRpIjoiZmI0OTdhNGEtOTliNi00MDJkLTg5ZjctNWM2ZWFlYWQ5N2E1IiwidXNlcl9kYXRhIjp7ImlkIjoxLCJ1c2VybmFtZSI6InZpbmljaXVzIiwiZW1haWwiOiJ2aW5pY2l1c0B0ZXN0ZS5jb20ifSwiZXhwIjoxNTE2ODAxNDE3LCJhdXRob3JpdGllcyI6WyJST0xFX1VTVUFSSU9fQ09NVU0iLCJyZWFkIl0sImp0aSI6IjEyYmQ5MDQ2LTk3YzktNGZiYi1iYmIyLTYyM2VkNGEwMDVkZiIsImNsaWVudF9pZCI6IndlYl9hcHAifQ.dNZAKI6puvufXVZyvls58bXssmkIw-sVxFTJJJ7mFWk",
    "expires_in": 43198,
    "scope": "read",
    "user_data": {
        "id": 1,
        "username": "vinicius",
        "email": "vinicius@teste.com"
    },
    "jti": "fb497a4a-99b6-402d-89f7-5c6eaead97a5"
  }
   ```

   **JWT Header**

   ```json
   {
     "alg":"HS256",
     "typ":"JWT"
   }
   ```

  **JWT Pyload**

  ```json
   {
    "aud": ["spotippos-api"],
    "user_name":"vinicius",
    "scope":["read"],
    "user_data": {
      "id":1,
      "username":"vinicius",
      "email":"vinicius@teste.com"
    },
    "exp":1514252617,
    "authorities":["ROLE_USUARIO_COMUM","read"],
    "jti":"fb497a4a-99b6-402d-89f7-5c6eaead97a5",
    "client_id":"web_app"
  }
  ```

   **Obs:** Por questão de segurança é importante que a redirect_uri esteja junto do client

## Atualizando o token

Para atualizar o token chamar a url abaixo passando grant_type=refresh_token e refresh_token - o refresh token recebido junto com o token. A resposta será a mesma do pedido de token

http://localhost:9999/oauth/token?grant_type=refresh_token&client_id=web_app&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...

# O que está feito

## Servidor de autenticação

1. Criado endpoints OAuth e token JWT sendo gerado corretamento
1. Autenticação de usuário utilizando `UserDetailsService`
1. Criada a tela de login
1. Servidor redirecionando de volta com o code e fornecendo o token após o segundo request

## API

1. API autorizando o acesso somente através do token

# O que falta fazer

## Servidor de autenticação

1. Logoff se possível

## Aplicação SPA

1. Criar a aplicação SPA com Angular
1. Validação de autenticão
1. Redirecionamento para o servidor de autenticação
1. Validar o token recebido

# Tecnologias utilizadas

## Auth Server

* Spring Boot
* OAuth2
* Spring Data
* HSQLDB
* JWT

## API

* Spring Boot
* Spring Data
* REST
* HATEOAS
* HSQLDB
* JUNIT

# Mais

Outras informações, referências e mais detalhes sobre cada projeto pode ser encontrado dentro da respectiva pasta

# Gerando keystore e certificado

Aqui iremos gerar a keystore e o certificado publico. A keystore será usada para gerar os tokens 
JWT e o certificado será utilizado pela API para poder ler o token

1. Crie a JWT token keystore

```
  keytool -genkeypair -alias mytestkey -keyalg RSA -dname "CN=Web Server,OU=Unit,O=Organization,L=City,ST=State,C=US" -keypass changeme -keystore jwt.jks -storepass letmein
```

2. Exporte o certificado

```
  keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
```

3. Copie a chave publica para o arquivo public.cert

4. Coloque o arquivo jwt.jks na pasta resources do projeto auth-server

5. Coloque o arquivo public.cert na pasta resources do projeto da API

## Comandos CURL para teste

### Pegar o token

  `curl -XPOST "web_app:mysecret@localhost:9999/oauth/token" -d "grant_type=password&username=canela&password=canela"`

### Detalhe da propriedade 1

  `curl -H "Authorization: Bearer %TOKEN%" "localhost:9090/properties/1"`

### Cadastro de imovel

  `curl -XPOST -H "Authorization: Bearer %TOKEN%" -H "Content-Type: application/json" "localhost:9090/properties" -d "{ \"title\": \"teste\", \"baths\": 3, \"beds\": 3, \"x\": 0, \"y\": 0, \"squareMeters\": 100, \"price\": 120000, \"description\": \"descricao\" }"`

  # Referências

  * [OAuth 2 Simplified](https://aaronparecki.com/oauth-2-simplified/)
  * [OAuth 2.0 Tutorial](http://tutorials.jenkov.com/oauth2/index.html)