package com.example.gateway;

import com.example.gateway.resolver.HostAddrKeyResolver;
import com.example.gateway.resolver.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Gateway3LimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gateway3LimiterApplication.class, args);
	}

	@Bean
	public HostAddrKeyResolver hostAddrKeyResolver() {
		return new HostAddrKeyResolver();
	}

//	@Bean
//	public UriKeyResolver uriKeyResolver() {
//		return new UriKeyResolver();
//	}
}
