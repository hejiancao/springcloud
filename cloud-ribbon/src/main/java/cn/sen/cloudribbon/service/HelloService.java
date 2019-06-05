package cn.sen.cloudribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    public String hello(String name) {
        return restTemplate.getForObject("http://EURAKE-CLIENT/hi?name=" + name, String.class);
    }
}
