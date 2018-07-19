package com.revature.assignforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableZuulProxy
//@RestController
@EnableEurekaClient
@CrossOrigin
public class GatewayServerApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GatewayServerApplication.class, args);
	}
}