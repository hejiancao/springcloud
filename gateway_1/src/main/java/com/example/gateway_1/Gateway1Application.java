package com.example.gateway_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Gateway1Application {

    public static void main(String[] args) {
        SpringApplication.run(Gateway1Application.class, args);
    }


    /**
     * 在spring cloud gateway中使用RouteLocator的Bean进行路由转发，将请求进行处理，最后转发到目标的下游服务。
     * 在本案例中，会将请求转发到http://httpbin.org:80这个地址上
     *
     * @author shaos
     * @date 2019/6/18 10:52
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                // 当请求的host有“*.hystrix.com”，都会进入该router，该router中有一个hystrix的filter,
                // 该filter可以配置名称、和指向性fallback的逻辑的地址，比如本案例中重定向到了“/fallback”
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }


    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        // Mono是一个Reactive stream，对外输出一个“fallback”字符串
        return Mono.just("fallback");
    }


}
