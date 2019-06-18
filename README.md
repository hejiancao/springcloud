# springcloud练习
本文是通过学习方志朋老师的关于SpringCloud博客进行的个人总结<br>
以下附上方志朋老师的博客地址：[点我进入](https://blog.csdn.net/forezp/article/details/70148833)

## [springcloud各版本区别介绍](https://blog.csdn.net/chen497147884/article/details/79896141)

## 第一篇：服务注册与发现
https://blog.csdn.net/forezp/article/details/81040925
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
https://blog.csdn.net/forezp/article/details/81040946<br>
[idea启动多个实例](https://blog.csdn.net/forezp/article/details/76408139)
## 第三篇：feign的方式进行接口调用
https://blog.csdn.net/forezp/article/details/81040965
## 第四篇：hystrix断路器
https://blog.csdn.net/forezp/article/details/81040990
>简介：在微服务架构中，根据业务来拆分成一个个的服务，服务与服务之间可以相互调用（RPC），在Spring Cloud可以用RestTemplate+Ribbon和Feign来调用。为了保证其高可用，单个服务通常会集群部署。由于网络原因或者自身的原因，服务并不能保证100%可用，如果单个服务出现问题，调用这个服务就会出现线程阻塞，此时若有大量的请求涌入，Servlet容器的线程资源会被消耗完毕，导致服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务故障的“雪崩”效应。
    为了解决这个问题，业界提出了断路器模型。
    
断路器实现有两种：<br>
1.通过rest + ribbon方式<br> 
2.通过feign方式

## 第五篇：zull路由网关
https://blog.csdn.net/forezp/article/details/81041012
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

## 第六篇：分布式配置中心(Spring Cloud Config)
https://blog.csdn.net/forezp/article/details/81041028
>简介：在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client。

## 第七篇：高可用的分布式配置中心(Spring Cloud Config)
https://blog.csdn.net/forezp/article/details/81041045
## 第八篇：消息总线(Spring Cloud Bus)
https://blog.csdn.net/forezp/article/details/81041062
>简介：Spring Cloud Bus 将分布式的节点用轻量的消息代理连接起来。它可以用于广播配置文件的更改或者服务之间的通讯，也可以用于监控。本文要讲述的是用Spring Cloud Bus实现通知微服务架构的配置文件的更改
## 第九篇：服务链路追踪(Spring Cloud Sleuth)
https://blog.csdn.net/forezp/article/details/81041078
>简介：这篇文章主要讲述服务追踪组件zipkin，Spring Cloud Sleuth集成了zipkin组件,Spring Cloud Sleuth 主要功能就是在分布式系统中提供追踪解决方案，并且兼容支持了 zipkin，你只需要在pom文件中引入相应的依赖即可。
微服务架构上通过业务来划分服务的，通过REST调用，对外暴露的一个接口，可能需要很多个服务协同才能完成这个接口功能，如果链路上任何一个服务出现问题或者网络超时，都会形成导致接口调用失败。随着业务的不断扩张，服务之间互相调用会越来越复杂。
随着服务的越来越多，对调用链的分析会越来越复杂。

作用：查看各服务之间调用关系，当其中一个服务出现问题时，方便定位问题<br>

1.下载[zipkin-jar](https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/)包，然后启动 java -jar zipkin-server-2.10.2-exec.jar, 访问http://localhost:9411/zipkin/<br>
2.为了追踪各服务，需要给微服务添加依赖
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```
3.配置文件修改
```
#指定ipkin server的地址
spring.zipkin.base-url=http://localhost:9411

```
4.接口调用之后，继续访问http://localhost:9411/zipkin/
## 第十篇：高可用的服务注册中心
https://blog.csdn.net/forezp/article/details/81041101
>简介：第一篇介绍了服务注册与发现，其中服务注册中心Eureka Server，是一个实例，当成千上万个服务向它注册的时候，它的负载是非常高的，这在生产环境上是不太合适的，这篇文章主要介绍怎么将Eureka Server集群化。

作用：注册中心集群化，实现高可用
## 第十一篇：
## 第十二篇：
## 第十三篇：

