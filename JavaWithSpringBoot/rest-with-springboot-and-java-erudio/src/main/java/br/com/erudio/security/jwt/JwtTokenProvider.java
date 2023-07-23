package br.com.erudio.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

	
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:3600000}")
	private long timeout = 3600000;	// 1h
	
	
	@Autowired
	private UserDetailsService userDetailService;
	
	private Algorithm algorithm = null;
	
	
	@PostConstruct
	protected void init() {
		
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
		
	}
	
	
	public TokenVO createAccessToken(String userName, List<String> roles) {
		
		Date now = new Date();
		Date validity = new Date(now.getTime() + timeout);
		
		String accessToken = getAccessToken(userName, roles, now, validity);
		String refreshToken = getRefreshToken(userName, roles, now);
		
		return new TokenVO(userName, true, now, validity, accessToken, refreshToken);
		
	}


	private String getAccessToken(String userName, List<String> roles, Date now, Date validity) {

		String urlSolicitacao = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.build()
				.toUriString();
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(userName)
				.withIssuer(urlSolicitacao)
				.sign(algorithm)
				.strip();
		
	}
	
	
	private String getRefreshToken(String userName, List<String> roles, Date now) {

		Date validityRefreshToken = new Date(now.getTime() + (timeout * 3)); 

		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshToken)
				.withSubject(userName)
				.sign(algorithm)
				.strip();

	}

	
	public Authentication getAuthentication(String token) {
		
		DecodedJWT decodedJWT = decodeToken(token);
		UserDetails userDetails = this.userDetailService.loadUserByUsername(decodedJWT.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		
	}


	private DecodedJWT decodeToken(String token) {

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);

		return decodedJWT;
		
	}
	
	
	public String getToken(HttpServletRequest req) {
		
		String bearerToken = req.getHeader("Authorization");	// Retorna no formato "Bearer <valor do token>"
		
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		
		return null;
		
	}
	
	
	public boolean validadeToken(String token) {
		
		DecodedJWT decodedJWT = decodeToken(token);
		
		try {
			
			if (decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Token inválido ou expirado.");
		}
		
	}
	
}
