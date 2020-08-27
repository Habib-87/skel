package com.cepheid.cloud.skel.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	 

	@Bean
	public Docket swaggerV1() {
      

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("v1")
				.apiInfo(portalApiInfo("V1"))
				.select().apis(RequestHandlerSelectors.basePackage("com.cepheid"))
				.paths(PathSelectors.regex("/app/api/1.0.*")).build();
	}
	

	    private ApiInfo portalApiInfo(String version) {

	        String description;
	        try {
	            InputStream resourceAsStream = SwaggerConfig.class.getResourceAsStream("/description.md");
	            description = IOUtils.toString(resourceAsStream);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }

	        return new ApiInfoBuilder()
	                .title("Item Catalog API")
	                .description(description)
	                .version(version)
	           .build();
	    }

}
