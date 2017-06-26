# Spotippos Authorization Server

Projeto de servidor de autenticação utilizando oAuth2 e JWT com Spring Boot.

## Descrição

Servidor de autenticação responsável autenticar o usuário e gerar o token de acesso para outras aplicações

## O que esta feito

1. Consigo gerar os token através do comando através dos endpoints através da chamada abaixo

   `curl -XPOST "web_app:@localhost:9999/oauth/token" -d "grant_type=password&username=canela&password=canela"`

1. Consigo fazer a autenticação através da tela de login

## O que falta

1. Após o usuário fazer a autenticação, gerar o token e redirecionar para a aplicação origem

## TODO

* Na tela de login receber client_id, client_secret, redirect_url
* Verificar como gerar o token pelo serviço para redirecionar de volta para aplicação origem

## OAuth endpoints

* /oauth/authorize
* /oauth/token
* /oauth/check_token
* /oauth/confirm_access
* /oauth/error
* /oauth/token_key

## Tecnologias

* Spring Boot
* OAuth2
* Spring Data
* HSQLDB
* JWT

## Referências

* [Securing Spring Cloud Microservices With OAuth2](http://stytex.de/blog/2016/02/01/spring-cloud-security-with-oauth2/)
* [Securing A Rest Service](https://spring.io/guides/tutorials/bookmarks/#_securing_a_rest_service)
* [Spring Boot + Spring MVC + Spring Security](https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d)