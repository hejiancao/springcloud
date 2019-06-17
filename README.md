# springcloud练习

## [springcloud各版本区别介绍](https://blog.csdn.net/chen497147884/article/details/79896141)

## 第一篇：添加注册中心
[参考链接](https://blog.csdn.net/forezp/article/details/81040925)<br>
>1.先创建父工程，使用IDEA，new -- project --spring initializr -- packaging为pom<br>
>2.创建eurake-server,new -- module -- spring initializr -- cloud discovery -- eurake server -- packaging为jar -- parent选择上一级center -- 并在上一级pom.xml中手动添加modules<br>
>3.在启动类上加@EnableEurekaServer<br>
>4.application.yml<br>
```
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

spring:
  application:
    name: eurka-server


```
>5.启动主类，访问http:localhost:8761,此时无服务注册进来<br>
>6.创建eurake-client，new -- module -- spring initializr -- cloud discovery -- eurake discovery -- packaging为jar -- parent选择上一级center -- 并在上一级pom.xml中手动添加modules<br>
>7.在启动类上加@EnableEurekaClient<br>
>8.application.yml<br>
```
server:
  port: 8762

spring:
  application:
    name: service-client

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/


```
>9.启动eurake-client，打开http:localhost:8761,发现有一台注册<br>

## 第二篇：rest+ribbon的方式进行接口调用
[参考链接](https://blog.csdn.net/forezp/article/details/81040946)<br>
[idea启动多个实例](https://blog.csdn.net/forezp/article/details/76408139)
## 第三篇：feign的方式进行接口调用
[参考链接](https://blog.csdn.net/forezp/article/details/81040965)
## 第四篇：hystrix断路器
>简介：在微服务架构中，根据业务来拆分成一个个的服务，服务与服务之间可以相互调用（RPC），在Spring Cloud可以用RestTemplate+Ribbon和Feign来调用。为了保证其高可用，单个服务通常会集群部署。由于网络原因或者自身的原因，服务并不能保证100%可用，如果单个服务出现问题，调用这个服务就会出现线程阻塞，此时若有大量的请求涌入，Servlet容器的线程资源会被消耗完毕，导致服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务故障的“雪崩”效应。
    为了解决这个问题，业界提出了断路器模型。
    
断路器实现有两种：<br>
1.通过rest + ribbon方式<br> 
2.通过feign方式

[参考链接](https://blog.csdn.net/forezp/article/details/81040990)
## 第五篇：zull路由网关
>简介：Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，比如／api/user转发到到user服务，/api/shop转发到到shop服务<br>
### 路由功能
配置文件
```
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8769
spring:
  application:
    name: cloud-zuul
zuul:
  routes:
    api-a:
      path: /api-a/**
      serviceId: cloud-ribbon
    api-b:
      path: /api-b/**
      serviceId: cloud-feign
```
### 过滤功能
略<br>
[参考链接](https://blog.csdn.net/forezp/article/details/81041012)
