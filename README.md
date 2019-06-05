# springcloud

#### 添加注册中心
[参考链接](https://blog.csdn.net/forezp/article/details/81040925)
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
