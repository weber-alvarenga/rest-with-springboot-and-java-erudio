package br.com.erudio.config;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;

import br.com.erudio.security.jwt.JwtConfigurer;
import br.com.erudio.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	// estudar https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter
	// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		
		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
		return passwordEncoder;
		
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				.httpBasic().disable()
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
																				"/auth/signin",
																				"/auth/refresh/**",
													                    		"/swagger-ui/**",
													                    		"/v3/api-docs/**"
																					).permitAll()
																	  .requestMatchers("/**").authenticated()
																	  .requestMatchers("/users").denyAll()
						
				)
				.cors()
				.and()
				.apply(new JwtConfigurer(tokenProvider))
				.and()
				.build();
				
	}
	
}
