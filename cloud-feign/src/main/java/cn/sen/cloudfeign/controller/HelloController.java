package cn.sen.cloudfeign.controller;

import cn.sen.cloudfeign.feign.SayHelloFromClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private SayHelloFromClient sayHelloFromClient;


    @GetMapping("/hi")
    public String hello(@RequestParam("name") String name) {
        return sayHelloFromClient.helloFromClient(name);
    }
}
