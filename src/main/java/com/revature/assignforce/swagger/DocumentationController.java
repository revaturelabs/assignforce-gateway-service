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

/**
 * Designed to collect the documentation from all services in order to display.
 * 
 * @author devon
 *
 */
@Component
@Primary
@EnableAutoConfiguration
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
				.apiInfo(new ApiInfo("Thing API", "We got things.", "thing TOS", "Terms of service",
						new Contact("Devon Virden", "https://i.imgur.com/g3MdE5u.gif", "devonjvirden@hotmail.com"),
						"License of API", "API license URL", Collections.emptyList())).enable(true);
	}
	
	/**
	 * Gets all the API-docs from all services.
	 */
	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
		resources.add(swaggerResource("trainerController", "/api/trainer/v2/api-docs", "2.0"));
		resources.add(swaggerResource("settingController", "/api/setting/v2/api-docs", "2.0"));
		resources.add(swaggerResource("unavailableController", "/api/unavailable/v2/api-docs", "2.0"));
		resources.add(swaggerResource("filehandlerController", "/api/filehandler/v2/api-docs", "2.0"));
		resources.add(swaggerResource("finalProjectController", "/api/finalProject/v2/api-docs", "2.0"));
		resources.add(swaggerResource("sprintService", "/api/sprint/v2/api-docs", "2.0"));
		resources.add(swaggerResource("skillController", "/api/skill/v2/api-docs", "2.0"));
		resources.add(swaggerResource("focusController", "/api/focus/v2/api-docs", "2.0"));
		resources.add(swaggerResource("curriculumController", "/api/curriculum/v2/api-docs", "2.0"));
		resources.add(swaggerResource("roomController", "/api/room/v2/api-docs", "2.0"));
		resources.add(swaggerResource("eventController", "/api/event/v2/api-docs", "2.0"));
		resources.add(swaggerResource("locationController", "/api/location/v2/api-docs", "2.0"));
		resources.add(swaggerResource("buildingController", "/api/building/v2/api-docs", "2.0"));
		resources.add(swaggerResource("batchController", "/api/batch/v2/api-docs", "2.0"));
		resources.add(swaggerResource("addressController", "/api/address/v2/api-docs", "2.0"));
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
