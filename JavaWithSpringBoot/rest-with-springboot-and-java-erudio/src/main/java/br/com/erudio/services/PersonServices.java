package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonServices {

	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	
	public List<Person> findAll() {
		
		logger.info("Listando todas as pessoas.");
		
		List<Person> persons = new ArrayList<>();
		
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		
		return persons;
	}


	public Person findById(String id) {
		
		logger.info("Procurando pessoa pelo ID.");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("José");
		person.setLastName("da Silva");
		person.setAddress("Alameda da rua 13");
		person.setGender("Masculino");
		
		return person;
	}

	
	public Person create(Person person) {
		
		logger.info("Inserindo uma pessoa.");
		
		person.setId(counter.incrementAndGet());
		
		return person;
	}
	
	
	public Person update(Person person) {
		
		logger.info("Atualizando uma pessoa.");
		
		person.setFirstName(person.getFirstName() + " Atualizado");
		
		return person;
	}


	public void delete(String id) {
		logger.info("Excluindo uma pessoa.");
	}

	private Person mockPerson(int i) {

		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Nome " + i);
		person.setLastName("Sobrenome " + i);
		person.setAddress("Rua " + i);
		person.setGender("Masculino");
		
		return person;

	}
}
