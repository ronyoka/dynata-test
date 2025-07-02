package com.dynata.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger/OpenAPI documentation for the application.
 * It defines the API documentation details such as the title, description, version,
 * contact information, and applicable license.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                              .title("Dynata Test Survey API")
                              .description("Spring Boot REST API for Dynata Test Survey API")
                              .version("1.0.0")
                              .contact(new Contact()
                                               .name("Robert Siket")
                                               .email("siketr@hotmail.com")
                                               .url("https://www.example.com"))
                              .license(new License()
                                               .name("Apache 2.0")
                                               .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}