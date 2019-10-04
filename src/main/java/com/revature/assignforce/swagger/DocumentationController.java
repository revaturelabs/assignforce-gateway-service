package com.revature.assignforce.swagger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Designed to collect the documentation from all services in order to display.
 * 
 * @author devon
 *
 */
@Component
@Primary
@EnableAutoConfiguration
@EnableSwagger2
public class DocumentationController implements SwaggerResourcesProvider {
	
	/**
	 * Provides basic information about the service, such as contact information and
	 * licensing.
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				.apiInfo(new ApiInfo("Gateway-Service", "Handles Zuul, connecting to the other services.", null, null,
						new Contact("August Duet", null, "august@revature.com"), null, null,
						Collections.emptyList())).enable(true);
	}
	
	/**
	 * Gets all the API-docs from all services.
	 */
	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
		resources.add(swaggerResource("Trainer Service", "/api/trainer-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("Setting Service", "/api/setting-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("Filehandler Service", "/api/filehandler-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("Skill Service", "/api/skill-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("Curriculum Servive", "/api/curriculum-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("Location Service", "/api/location-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("buildingController", "/api/building/v2/api-docs", "2.0"));
		resources.add(swaggerResource("Batch Service", "/api/batch-service/v2/api-docs", "2.0"));
		return resources;
	}

	/**
	 * Sets the Swagger resource, telling Swagger where to search for API-docs.
	 * 
	 * @param name
	 * @param location
	 * @param version
	 * @return
	 */
	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}

}
