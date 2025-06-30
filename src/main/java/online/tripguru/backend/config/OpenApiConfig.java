package online.tripguru.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

//    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Trip Guru Backend").version("1.0.0")
                        .description("About\n" +
                                "Backend for a personalized AI-powered travel assistant that helps users plan their trips end-to-end"))
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080")
                        )
                );
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
//                                new SecurityScheme()
//                                        .name("Authorization")
//                                        .type(Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")
//                                        .in(In.HEADER)
//                        )
//                );
    }
}
