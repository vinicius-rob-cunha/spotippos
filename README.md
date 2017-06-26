# Spotippos

Projeto DEMO para desenvolver uma aplicação SPA com Angula com servidor de autenticação
e consumindo uma API RESTful, ambos servidores com Spring Boot, OAuth2 e JWT

# Descrição / Objetivo

O objetivo é usuário acessar a aplicação angular e caso não esteja autentica redirecionar para o 
servidor de autenticação.

O servidor de autenticação por sua vez irá exibir a página de login e após a autenticação retornar para a aplicação que chamou informando o token jwt

A aplicação origem armazena esse token e realiza as chamadas para a API RESTful através dele

# O que está feito

## Servidor de autenticação

1. Criado endpoints OAuth e token JWT sendo gerado corretamento
1. Autenticação de usuário utilizando `UserDetailsService`
1. Criada a tela de login

## API

1. API autorizando o acesso somente através do token

# O que falta fazer

## Servidor de autenticação

1. Gerar o token após o login e redirecionar para a aplicação origem

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