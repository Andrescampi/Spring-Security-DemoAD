package com.securitydemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user and generate JWT-like token",
            description = "Authenticates a user and returns a Base64 encoded token."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful authentication",
                    content = @Content(
                            schema = @Schema(
                                    type = "string",
                                    example = "Bearer eyJzdWIiOiAidXNlciIsICJyb2xlIjogIlJPTEVfVVNFUiJ9"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public String login(
            @Parameter(description = "Username", required = true) @RequestParam String username,
            @Parameter(description = "Password", required = true) @RequestParam String password
    ) {
        if (("user".equals(username) && "password".equals(password)) ||
                ("admin".equals(username) && "adminpass".equals(password))) {

            String role = "user".equals(username) ? "USER" : "ADMIN";
            String token = Base64.getEncoder().encodeToString(
                    ("{\"sub\": \"" + username + "\", \"role\": \"ROLE_" + role + "\"}").getBytes()
            );
            return "Bearer " + token;
        }
        throw new RuntimeException("Invalid credentials");
    }
}
