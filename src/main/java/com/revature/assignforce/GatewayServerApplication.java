package com.revature.assignforce;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@CrossOrigin
public class GatewayServerApplication {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(GatewayServerApplication.class).run(args);
	}
}