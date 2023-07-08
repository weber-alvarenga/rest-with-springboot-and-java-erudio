package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOv2;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper customPersonMapper;
	
	
	
	public List<PersonVO> findAll() throws Exception {
		
		logger.info("Listando todas as pessoas.");
		
		List<PersonVO> persons = DozerMapper.parseObjectsList(repository.findAll(), PersonVO.class);

		persons
			.stream()
			.forEach(p -> {
				try {
					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		
		return persons;
		
		/*
		List<PersonVO> resultado = new ArrayList<PersonVO>();
		
		for (PersonVO personVO : persons) {
			personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
			resultado.add(personVO);
		}
		
		return resultado;
		*/
	}


	public PersonVO findById(Long id) throws Exception {
		
		logger.info("Procurando pessoa pelo ID.");

		Person personAux = repository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;
		
		PersonVO vo = DozerMapper.parseObject(personAux, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	
	public PersonVO create(PersonVO person) throws Exception {
		
		if (person == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Inserindo uma pessoa.");
		
		Person personAux = DozerMapper.parseObject(person, Person.class);
		
		PersonVO vo = DozerMapper.parseObject(repository.save(personAux), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;		
	}

	
	public PersonVOv2 createv2(PersonVOv2 person) {
		
		logger.info("Inserindo uma pessoa na versão 2.");
		
		Person personAux = customPersonMapper.convertVoToEntity(person);
		
		return customPersonMapper.convertEntytyToVo(repository.save(personAux));
	}
	
	
	public PersonVO update(PersonVO person) throws Exception {

		if (person == null) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Atualizando uma pessoa.");
		
		Person personBD = repository.findById(person.getKey())
							.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		personBD.setFirstName(person.getFirstName());
		personBD.setLastName(person.getLastName());
		personBD.setAddress(person.getAddress());
		personBD.setGender(person.getGender());

		PersonVO vo = DozerMapper.parseObject(repository.save(personBD), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
		return vo;

	}


	public void delete(Long id) {
		logger.info("Excluindo uma pessoa.");
		
		Person personBD = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		repository.delete(personBD);
	}

}
