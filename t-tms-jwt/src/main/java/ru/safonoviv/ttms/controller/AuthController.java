package ru.safonoviv.ttms.controller;

import lombok.RequiredArgsConstructor;
import ru.safonoviv.ttms.dto.JwtRequest;
import ru.safonoviv.ttms.dto.RegistrationUserDto;
import ru.safonoviv.ttms.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController()
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {
	private final AuthService authService;

	@Operation(description = "Sign in service", responses = {
			@ApiResponse(responseCode = "200", ref = "successfulАuthorization"),
			@ApiResponse(responseCode = "401", ref = "unautorized") })
	@PostMapping("/auth")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
		return authService.createAuthToken(authRequest);
	}

	@Operation(description = "Register in service", responses = {
			@ApiResponse(responseCode = "200", ref = "successfulRegister"),
			@ApiResponse(responseCode = "400", ref = "registerBadRequest") })
	@PostMapping("/registration")
	public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
		return authService.createNewUser(registrationUserDto);
	}
}