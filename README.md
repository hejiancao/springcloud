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

spring官方有用spring-cloud-gateway替代zull的意思，gateway是第二代网关框架
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


## 第十一篇：断路器监控(Hystrix Dashboard)
https://blog.csdn.net/forezp/article/details/81041113
>简介：在微服务架构中为例保证程序的可用性，防止程序出错导致网络阻塞，出现了断路器模型。断路器的状况反应了一个程序的可用性和健壮性，它是一个重要指标。Hystrix Dashboard是作为断路器状态的一个组件，提供了数据监控和友好的图形化界面。

作用：为断路器提供友好图形化界面


## 第十二篇：断路器聚合监控(Hystrix Turbine)
https://blog.csdn.net/forezp/article/details/81041125
>简介：看单个的Hystrix Dashboard的数据并没有什么多大的价值，要想看这个系统的Hystrix Dashboard数据就需要用到Hystrix Turbine。Hystrix Turbine将每个服务Hystrix Dashboard数据进行了整合

作用：将每个服务Hystrix Dashboard数据进行了整合


## 第十三篇：Spring Cloud Gateway初体验
https://blog.csdn.net/forezp/article/details/83792388
>简介：Spring Cloud Gateway是Spring Cloud官方推出的第二代网关框架，取代Zuul网关。网关作为流量的，在微服务系统中有着非常作用，网关常见的功能有路由转发、权限校验、限流控制等作用

作用：第二代网关框架，取代Zull网关<br>
注意事项：<br>
1.注意移除依赖spring-boot-starter-web，不然启动不了<br>
2.注意springboot和springcloud版本兼容问题，我的springboot2.1.5.RELEASE,springcloud用G版本，用F版本会报错<br>


## 第十四篇：Spring Cloud Gateway之Predict篇
https://blog.csdn.net/forezp/article/details/84926662
>简介：在下图中，有很多类型的Predicate,比如说时间类型的Predicated（AfterRoutePredicateFactory BeforeRoutePredicateFactory BetweenRoutePredicateFactory），当只有满足特定时间要求的请求会进入到此predicate中，并交由router处理；cookie类型的CookieRoutePredicateFactory，指定的cookie满足正则匹配，才会进入此router;以及host、method、path、querparam、remoteaddr类型的predicate，每一种predicate都会对当前的客户端请求进行判断，是否满足当前的要求，如果满足则交给当前请求处理。如果有很多个Predicate，并且一个请求满足多个Predicate，则按照配置的顺序第一个生效。

![predict](https://github.com/hejiancao/springcloud/blob/master/pics/20190601010743550.png)
作用：路由


## 第十五篇：Spring Cloud Gateway之filter篇
https://blog.csdn.net/forezp/article/details/85057268<br>
作用：过滤


## 第十六篇：Spring Cloud Gateway之限流篇
https://blog.csdn.net/forezp/article/details/85081162
>简介：在高并发的系统中，往往需要在系统中做限流，一方面是为了防止大量的请求使服务器过载，导致服务不可用，另一方面是为了防止网络攻击。

### 计数器算法
计数器算法采用计数器实现限流有点简单粗暴，一般我们会限制一秒钟的能够通过的请求数，比如限流qps为100，算法的实现思路就是从第一个请求进来开始计时，在接下去的1s内，每来一个请求，就把计数加1，如果累加的数字达到了100，那么后续的请求就会被全部拒绝。等到1s结束后，把计数恢复成0，重新开始计数。具体的实现可以是这样的：对于每次服务调用，可以通过AtomicLong#incrementAndGet()方法来给计数器加1并返回最新值，通过这个最新值和阈值进行比较。这种实现方式，相信大家都知道有一个弊端：如果我在单位时间1s内的前10ms，已经通过了100个请求，那后面的990ms，只能眼巴巴的把请求拒绝，我们把这种现象称为“突刺现象”
### 漏桶算法
漏桶算法为了消除"突刺现象"，可以采用漏桶算法实现限流，漏桶算法这个名字就很形象，算法内部有一个容器，类似生活用到的漏斗，当请求进来时，相当于水倒入漏斗，然后从下端小口慢慢匀速的流出。不管上面流量多大，下面流出的速度始终保持不变。不管服务调用方多么不稳定，通过漏桶算法进行限流，每10毫秒处理一次请求。因为处理的速度是固定的，请求进来的速度是未知的，可能突然进来很多请求，没来得及处理的请求就先放在桶里，既然是个桶，肯定是有容量上限，如果桶满了，那么新进来的请求就丢弃。在算法实现方面，可以准备一个队列，用来保存请求，另外通过一个线程池（ScheduledExecutorService）来定期从队列中获取请求并执行，可以一次性获取多个并发执行。
这种算法，在使用过后也存在弊端：无法应对短时间的突发流量。
![](https://github.com/hejiancao/springcloud/blob/master/pics/%E6%9C%A8%E6%A1%B6%E7%AE%97%E6%B3%95.png)
### 令牌桶算法
从某种意义上讲，令牌桶算法是对漏桶算法的一种改进，桶算法能够限制请求调用的速率，而令牌桶算法能够在限制调用的平均速率的同时还允许一定程度的突发调用。在令牌桶算法中，存在一个桶，用来存放固定数量的令牌。算法中存在一种机制，以一定的速率往桶中放令牌。每次请求调用需要先获取令牌，只有拿到令牌，才有机会继续执行，否则选择选择等待可用的令牌、或者直接拒绝。放令牌这个动作是持续不断的进行，如果桶中令牌数达到上限，就丢弃令牌，所以就存在这种情况，桶中一直有大量的可用令牌，这时进来的请求就可以直接拿到令牌执行，比如设置qps为100，那么限流器初始化完成一秒后，桶中就已经有100个令牌了，这时服务还没完全启动好，等启动完成对外提供服务时，该限流器可以抵挡瞬时的100个请求。所以，只有桶中没有令牌时，请求才会进行等待，最后相当于以一定的速率执行。
实现思路：可以准备一个队列，用来保存令牌，另外通过一个线程池定期生成令牌放到队列中，每来一个请求，就从队列中获取一个令牌，并继续执行。
![](https://github.com/hejiancao/springcloud/blob/master/pics/%E4%BB%A4%E7%89%8C%E6%A1%B6%E7%AE%97%E6%B3%95.PNG)
## 第十七篇：Spring Cloud Gateway
>简介：
