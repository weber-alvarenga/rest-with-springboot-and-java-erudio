package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	
	public List<Person> findAll() {
		
		logger.info("Listando todas as pessoas.");
		
		return repository.findAll();
	}


	public Person findById(Long id) {
		
		logger.info("Procurando pessoa pelo ID.");
		/*
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("José");
		person.setLastName("da Silva");
		person.setAddress("Alameda da rua 13");
		person.setGender("Masculino");
		*/
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;
	}

	
	public Person create(Person person) {
		
		logger.info("Inserindo uma pessoa.");
		
		return repository.save(person);
	}
	
	
	public Person update(Person person) {
		
		logger.info("Atualizando uma pessoa.");
		
		Person personBD = repository.findById(person.getId())
							.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		personBD.setFirstName(person.getFirstName());
		personBD.setLastName(person.getLastName());
		personBD.setAddress(person.getAddress());
		personBD.setGender(person.getGender());

		return repository.save(personBD);
	}


	public void delete(Long id) {
		logger.info("Excluindo uma pessoa.");
		
		Person personBD = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		repository.delete(personBD);
	}

}
