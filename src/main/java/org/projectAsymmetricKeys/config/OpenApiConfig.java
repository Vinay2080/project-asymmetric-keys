package org.projectAsymmetricKeys.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring security JWT asymmetric keys",
                        email = "shaking.121@gmail.com",
                        url = "https://vinay.com"
                ),
                description = "OpenAPI documentation for Spring Security",
                title = "OpenAPI Specification",
                version = "1.0",
                license = @License(
                        name = "licence name",
                        url = "https://vinay.com/licence"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "local environment",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "production environment",
                        url = "http://your.prod.env"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
