package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	
	public List<PersonVO> findAll() {
		
		logger.info("Listando todas as pessoas.");
		
		return DozerMapper.parseObjectsList(repository.findAll(), PersonVO.class);
	}


	public PersonVO findById(Long id) {
		
		logger.info("Procurando pessoa pelo ID.");

		Person personAux = repository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;
		
		return DozerMapper.parseObject(personAux, PersonVO.class);
	}

	
	public PersonVO create(PersonVO person) {
		
		logger.info("Inserindo uma pessoa.");
		
		Person personAux = DozerMapper.parseObject(person, Person.class);
		
		return DozerMapper.parseObject(repository.save(personAux), PersonVO.class);
	}
	
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Atualizando uma pessoa.");
		
		Person personBD = repository.findById(person.getId())
							.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		personBD.setFirstName(person.getFirstName());
		personBD.setLastName(person.getLastName());
		personBD.setAddress(person.getAddress());
		personBD.setGender(person.getGender());

		return DozerMapper.parseObject(repository.save(personBD), PersonVO.class);
	}


	public void delete(Long id) {
		logger.info("Excluindo uma pessoa.");
		
		Person personBD = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		repository.delete(personBD);
	}

}
