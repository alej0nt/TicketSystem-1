package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.LoginRequestDTO;
import com.leoalelui.ticketsystem.domain.dto.response.JwtResponseDTO;
import com.leoalelui.ticketsystem.domain.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación con JWT")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y devuelve un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        JwtResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
