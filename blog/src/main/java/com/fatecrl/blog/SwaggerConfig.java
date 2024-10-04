package com.fatecrl.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI blogAPIInfo(){
        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        info.title("API do Blog da FATEC");
        info.description("Esta API é utilizada na disciplina de Desenvolvimento para Servidores II");
        info.version("v1.0.0");
        License license = new License();
        license.name("License: None");
        info.license(license);
        Contact contact = new Contact();
        contact.url("https://diegoneri.com");
        contact.name("Diego Neri");
        contact.email("diego.felix@fatec.sp.gov.br");
        info.contact(contact);
        openAPI.info(info);

        return openAPI;

    //  return new OpenAPI().info(new Info()
    //     .title("API do Projeto Finanças da FATEC")
    //     .description("Esta API é utilizada na disciplina Desenvolvimento para Servidores II")
    //     .version("v0.0.1")
    //     .contact(new Contact()
    //       .name("Diego Neri").email("diego@diegoneri.com")));
  }
}
