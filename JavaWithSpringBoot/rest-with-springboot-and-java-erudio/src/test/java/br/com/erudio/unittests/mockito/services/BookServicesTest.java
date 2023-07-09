package br.com.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.services.BookServices;
import br.com.erudio.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input;
	
	@InjectMocks
	private BookServices bookServices;
	
	@Mock
	private BookRepository bookRepository;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
		
	}

	
	@Test
	void testFindById() {
		
		Book book = input.mockEntity(1);
		//book.setId(1L);
		
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		
		try {
			
			BookVO resultado = bookServices.findById(1L);
			
			assertNotNull(resultado);
			assertNotNull(resultado.getKey());
			assertNotNull(resultado.getLinks());
			assertTrue(resultado.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
			assertEquals("Author Name Test1", resultado.getAuthor());
			assertNotNull(resultado.getLaunchDate());
			assertEquals(10, resultado.getPrice());
			assertEquals("Title Test1", resultado.getTitle());
			
			
		} catch (Exception e) {
			fail("Exceção");
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	void testFindAll() throws Exception {
		
		List<Book> bookList = input.mockEntityList();
		
		when(bookRepository.findAll()).thenReturn(bookList);
		
		List<BookVO> books = bookServices.findAll();
		
		assertNotNull(books);
		assertEquals(14, books.size());

		BookVO bookOne = books.get(1);
		
		assertNotNull(bookOne);
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		assertTrue(bookOne.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Name Test1", bookOne.getAuthor());
		assertNotNull(bookOne.getLaunchDate());
		assertEquals(10, bookOne.getPrice());
		assertEquals("Title Test1", bookOne.getTitle());

		BookVO bookFour = books.get(4);
		
		assertNotNull(bookFour);
		assertNotNull(bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		assertTrue(bookFour.toString().contains("links: [</book/v1/4>;rel=\"self\"]"));
		assertEquals("Author Name Test4", bookFour.getAuthor());
		assertNotNull(bookFour.getLaunchDate());
		assertEquals(40, bookFour.getPrice());
		assertEquals("Title Test4", bookFour.getTitle());

		BookVO bookSeven = books.get(7);
		
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		assertTrue(bookSeven.toString().contains("links: [</book/v1/7>;rel=\"self\"]"));
		assertEquals("Author Name Test7", bookSeven.getAuthor());
		assertNotNull(bookSeven.getLaunchDate());
		assertEquals(70, bookSeven.getPrice());
		assertEquals("Title Test7", bookSeven.getTitle());

	}


	@Test
	void testCreate() {
		
		Book book = input.mockEntity(1);
		
		Book bookPesisted = book;
		//bookPersisted.setId(1L);
		
		BookVO vo = input.mockVO(1);

		when(bookRepository.save(book)).thenReturn(bookPesisted);
		
		try {
			
			BookVO resultado = bookServices.create(vo);
			
			assertNotNull(resultado);
			assertNotNull(resultado.getKey());
			assertNotNull(resultado.getLinks());
			assertTrue(resultado.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
			assertEquals("Author Name Test1", resultado.getAuthor());
			assertNotNull(resultado.getLaunchDate());
			assertEquals(10, resultado.getPrice());
			assertEquals("Title Test1", resultado.getTitle());
			
			
		} catch (Exception e) {
			fail("Exceção: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	void testCreateWithNullBook() {
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			bookServices.create(null);
		});
		
		String mensagemEsperada = "Não é possível salvar um objeto nulo.";
		String mensagemRecebida = exception.getMessage();
		
		assertTrue(mensagemRecebida.contains(mensagemEsperada));
		
	}


	@Test
	void testUpdate() {

		Book book = input.mockEntity(1);
		
		Book bookPesisted = book;
		
		BookVO vo = input.mockVO(1);

		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		when(bookRepository.save(book)).thenReturn(bookPesisted);
		
		try {
			
			BookVO resultado = bookServices.update(vo);
			
			assertNotNull(resultado);
			assertNotNull(resultado.getKey());
			assertNotNull(resultado.getLinks());
			assertTrue(resultado.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
			assertEquals("Author Name Test1", resultado.getAuthor());
			assertNotNull(resultado.getLaunchDate());
			assertEquals(10, resultado.getPrice());
			assertEquals("Title Test1", resultado.getTitle());
			
			
		} catch (Exception e) {
			fail("Exceção: " + e.getMessage());
			e.printStackTrace();
		}

	}


	@Test
	void testUpdateWithNullBook() {
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			bookServices.create(null);
		});
		
		String mensagemEsperada = "Não é possível salvar um objeto nulo.";
		String mensagemRecebida = exception.getMessage();
		
		assertTrue(mensagemRecebida.contains(mensagemEsperada));
		
	}

	
	@Test
	void testDelete() {

		Book book = input.mockEntity(1);
		//book.setId(1L);
		
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		
		try {
			
			bookServices.delete(1L);
			
		} catch (Exception e) {
			fail("Exceção");
			e.printStackTrace();
		}

	}

}
