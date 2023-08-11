package br.com.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthServices authServices;
	
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Autentica um usuário e retorna um token.")
	@PostMapping(value = "/signin")
	public ResponseEntity signIn(@RequestBody AccountCredentialsVO credentials) {
		
		if (credentials == null ||
			credentials.getUserName() == null ||
			credentials.getUserName().isBlank() ||
			credentials.getPassword() == null ||
			credentials.getPassword().isBlank())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida.");
		
		ResponseEntity token = authServices.signIn(credentials);
		
		if (token == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais inválidas.");
		
		return token;
		
	}
			

}
