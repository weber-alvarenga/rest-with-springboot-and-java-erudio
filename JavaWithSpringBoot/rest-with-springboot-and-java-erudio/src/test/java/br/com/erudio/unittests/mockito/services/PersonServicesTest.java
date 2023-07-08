package br.com.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	MockPerson input;
	
	@InjectMocks
	private PersonServices personServices;
	
	@Mock
	private PersonRepository personRepository;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
		
	}

	
	@Test
	void testFindById() {
		
		Person person = input.mockEntity(1);
		//person.setId(1L);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		try {
			
			PersonVO resultado = personServices.findById(1L);
			
			assertNotNull(resultado);
			assertNotNull(resultado.getKey());
			assertNotNull(resultado.getLinks());
			assertTrue(resultado.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
			assertEquals("First Name Test1", resultado.getFirstName());
			assertEquals("Last Name Test1", resultado.getLastName());
			assertEquals("Addres Test1", resultado.getAddress());
			assertEquals("Female", resultado.getGender());
			
			
		} catch (Exception e) {
			fail("Exceção");
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	void testFindAll() throws Exception {
		
		List<Person> personList = input.mockEntityList();
		
		when(personRepository.findAll()).thenReturn(personList);
		
		List<PersonVO> persons = personServices.findAll();
		
		assertNotNull(persons);
		assertEquals(14, persons.size());

		PersonVO personOne = persons.get(1);
		
		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		assertTrue(personOne.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("Addres Test1", personOne.getAddress());
		assertEquals("Female", personOne.getGender());

		PersonVO personFour = persons.get(4);
		
		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());
		assertTrue(personFour.toString().contains("links: [</person/v1/4>;rel=\"self\"]"));
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("Addres Test4", personFour.getAddress());
		assertEquals("Male", personFour.getGender());

		PersonVO personSeven = persons.get(7);
		
		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());
		assertTrue(personSeven.toString().contains("links: [</person/v1/7>;rel=\"self\"]"));
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("Addres Test7", personSeven.getAddress());
		assertEquals("Female", personSeven.getGender());

	}


	@Test
	void testCreate() {
		
		Person person = input.mockEntity(1);
		
		Person personPersisted = person;
		//personPersisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);

		when(personRepository.save(person)).thenReturn(personPersisted);
		
		try {
			
			PersonVO resultado = personServices.create(vo);
			
			assertNotNull(resultado);
			assertNotNull(resultado.getKey());
			assertNotNull(resultado.getLinks());
			assertTrue(resultado.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
			assertEquals("First Name Test1", resultado.getFirstName());
			assertEquals("Last Name Test1", resultado.getLastName());
			assertEquals("Addres Test1", resultado.getAddress());
			assertEquals("Female", resultado.getGender());
			
			
		} catch (Exception e) {
			fail("Exceção: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	void testCreateWithNullPerson() {
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			personServices.create(null);
		});
		
		String mensagemEsperada = "Não é possível salvar um objeto nulo.";
		String mensagemRecebida = exception.getMessage();
		
		assertTrue(mensagemRecebida.contains(mensagemEsperada));
		
	}


	@Test
	void testUpdate() {

		Person person = input.mockEntity(1);
		
		Person personPersisted = person;
		
		PersonVO vo = input.mockVO(1);

		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		when(personRepository.save(person)).thenReturn(personPersisted);
		
		try {
			
			PersonVO resultado = personServices.update(vo);
			
			assertNotNull(resultado);
			assertNotNull(resultado.getKey());
			assertNotNull(resultado.getLinks());
			assertTrue(resultado.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
			assertEquals("First Name Test1", resultado.getFirstName());
			assertEquals("Last Name Test1", resultado.getLastName());
			assertEquals("Addres Test1", resultado.getAddress());
			assertEquals("Female", resultado.getGender());
			
			
		} catch (Exception e) {
			fail("Exceção: " + e.getMessage());
			e.printStackTrace();
		}

	}


	@Test
	void testUpdateWithNullPerson() {
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			personServices.create(null);
		});
		
		String mensagemEsperada = "Não é possível salvar um objeto nulo.";
		String mensagemRecebida = exception.getMessage();
		
		assertTrue(mensagemRecebida.contains(mensagemEsperada));
		
	}

	
	@Test
	void testDelete() {

		Person person = input.mockEntity(1);
		//person.setId(1L);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		try {
			
			personServices.delete(1L);
			
		} catch (Exception e) {
			fail("Exceção");
			e.printStackTrace();
		}

	}

}
