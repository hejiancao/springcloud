package cn.sen.cloudribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod ="hiError")
    public String hello(String name) {
        return restTemplate.getForObject("http://EURAKE-CLIENT/hi?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi " + name + ", sorry error";
    }
}
