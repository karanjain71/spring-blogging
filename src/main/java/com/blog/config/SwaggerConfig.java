package com.blog.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	private ApiInfo getInfo() {
//		ApiInfo apiInfo = new ApiInfo("Blogging Application: Backend Practice","karanjain71@gmail.com","", "", "Eclipse Vorto Team", "EPL", "https://www.eclipse.org/legal/epl-2.0");
		ApiInfo apiInfo = new ApiInfo(
			      "Fullstop API",
			      "Audit reporting",
			      "",
			      "",
			      ApiInfo.DEFAULT_CONTACT,
			      "Apache 2.0",
			      "http://www.apache.org/licenses/LICENSE-2.0.html",
			      new ArrayList<VendorExtension>());
		return apiInfo;
	}
	

	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

}
