# Spotippos Authorization Server

Projeto de servidor de autenticação utilizando oAuth2 e JWT com Spring Boot.

## Descrição

Servidor de autenticação responsável autenticar o usuário e gerar o token de acesso para outras aplicações

## OAuth endpoints

* /oauth/authorize -> chamado junto com client_id, response_type=code e redirect_uri encaminha para o login e retorna para a aplicação origem depois
* /oauth/token -> passado junto com grant_type, username e password gera o token
* /oauth/check_token -> 
* /oauth/confirm_access ->
* /oauth/error ->
* /oauth/token_key ->

## Tecnologias

* Spring Boot
* OAuth2
* Spring Data
* HSQLDB
* JWT

## Referências

* [Securing Spring Cloud Microservices With OAuth2](http://stytex.de/blog/2016/02/01/spring-cloud-security-with-oauth2/)
* [Securing A Rest Service](https://spring.io/guides/tutorials/bookmarks/#_securing_a_rest_service)
* [Single login page within authorization server using Spring Boot and OAUTH2](https://stackoverflow.com/questions/43613052/single-login-page-within-authorization-server-using-spring-boot-and-oauth2)
* [Spring Boot + Spring MVC + Spring Security](https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d)