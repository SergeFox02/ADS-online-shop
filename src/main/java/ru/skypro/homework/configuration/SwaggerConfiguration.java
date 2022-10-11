package ru.skypro.homework.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("ADS-ONLINE shop API")
                                .version("0.1.1")
                                .contact(
                                        new Contact()
                                                .url("https://github.com/SergeFox02/ADS-online-shop")
                                                .name("Quartet J")
                                )
                );
    }
}
