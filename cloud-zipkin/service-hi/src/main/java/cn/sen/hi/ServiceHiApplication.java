package cn.sen.hi;


import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ServiceHiApplication {

	private static Logger log = LoggerFactory.getLogger(ServiceHiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceHiApplication.class, args);
	}

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }


	@RequestMapping("/hi")
	public String callHome(){
		log.info("calling trace service-hi  ");
		return restTemplate.getForObject("http://localhost:8989/miya", String.class);
	}


	@RequestMapping("/info")
	public String info(){
		log.info("calling trace service-hi ");
		return "i'm service-hi";
	}


}
