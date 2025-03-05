package com.acl.ticketinggateway.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(info = @Info(title = "Projet Parc Ticketing API",
        description = "API pour l'achat des tickets au parc du PAL(Port Autonome de Lomé)",
        version = "1.0",
        contact = @Contact(
                name = "PDG: Phillipe BOCCO",
                email = "gssamail@yahoo.com",
                url = "https://africaconsultingleaders.com/"
        ),
        license = @License(
                name = "ACL Licence",
                url = "https://africaconsultingleaders.com/"
        )
))
public class OpenApiConfiguration {
}
