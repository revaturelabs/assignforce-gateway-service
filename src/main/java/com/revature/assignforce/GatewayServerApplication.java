package com.revature.assignforce;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableWebSecurity
public class GatewayServerApplication {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(GatewayServerApplication.class).run(args);
	}

	@Bean
	public CorsFilter cors() {
		System.out.println("USING YOUR CORS FILTER");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("https://assignforce2.revaturelabs.com");
		config.addAllowedOrigin("https://dev2.assignforce.revaturelabs.com");
		config.addAllowedOrigin("https://assignforce.revaturelabs.com");
		config.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
		config.setAllowedMethods(Arrays.asList("GET", "POST","PUT", "DELETE","OPTIONS","HEAD", "PATCH"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}