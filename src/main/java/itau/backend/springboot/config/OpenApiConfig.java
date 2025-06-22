package itau.backend.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Transações e Estatísticas - Desafio Itaú Backend")
                        .version("1.0.0")
                        .description("Esta API RESTful gerencia transações financeiras em memória e fornece estatísticas em tempo real. " +
                                "Foi desenvolvida como parte de um desafio de programação para uma vaga de backend no Itaú.")
                );
    }
}
