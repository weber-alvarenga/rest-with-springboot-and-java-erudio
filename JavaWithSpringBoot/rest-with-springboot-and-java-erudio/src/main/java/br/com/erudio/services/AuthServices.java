package br.com.erudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.model.User;
import br.com.erudio.repositories.UserRepository;
import br.com.erudio.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {

	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signIn(AccountCredentialsVO credentials) {
		
		try {
			
			String username = credentials.getUserName();
			String password = credentials.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			User user = userRepository.findByUserName(username);
			TokenVO tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Usuário " + username + " não encontrado.");
			}
			
			return ResponseEntity.ok(tokenResponse);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Usuário ou senha inválidos.");
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshAccessToken(String username, String refreshToken) {
		
		// Pode-se autenticar o usuário novamente conforme nas duas primeiras linhas de signIn se desejar.
		
		User user = userRepository.findByUserName(username);
		TokenVO tokenResponse = new TokenVO();
		
		if (user != null) {
			tokenResponse = tokenProvider.refreshAccessToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado.");
		}
		
		return ResponseEntity.ok(tokenResponse);
		
	}
	
}
