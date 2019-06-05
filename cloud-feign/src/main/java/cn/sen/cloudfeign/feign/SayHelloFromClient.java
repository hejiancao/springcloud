package cn.sen.cloudfeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 通过feign调用eurake-client端的服务
 * @author shaos
 */
@FeignClient(value = "eurake-client", fallback = SayHelloFromClient.SayHelloFromClientHystrix.class)
public interface SayHelloFromClient {

    @GetMapping("/hi")
    String helloFromClient(@RequestParam("name") String name);



    @Component
    class SayHelloFromClientHystrix implements SayHelloFromClient {
        @Override
        public String helloFromClient(String name) {
            return "sorry " + name;
        }
    }


}
