package com.securitydemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Demo", description = "Endpoints for demonstrating security roles")
public class DemoController {

    @GetMapping("/user-endpoint")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "User access", description = "Requires USER role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public String userAccess() {
        return "Hello User! You have USER role access.";
    }

    @GetMapping("/admin-endpoint")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin access", description = "Requires ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public String adminAccess() {
        return "Hello Admin! You have ADMIN role access.";
    }

    @GetMapping("/common-endpoint")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Common access", description = "Requires USER or ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public String commonAccess() {
        return "Hello! Both USER and ADMIN roles can access this endpoint.";
    }
}