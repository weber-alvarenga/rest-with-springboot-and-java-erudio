package br.com.erudio.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.model.User;
import br.com.erudio.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService {

	private Logger logger = Logger.getLogger(UserServices.class.getName());

	@Autowired
	UserRepository repository;


	public UserServices(UserRepository repository) {

		this.repository = repository;
		
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("Procurando usuário pelo nome " + username + ".");
		
		User user = repository.findByUserName(username);
		
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
		}

	}


}
