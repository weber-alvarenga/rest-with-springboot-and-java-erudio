package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;

	
	
	public List<BookVO> findAll() throws Exception {
		
		logger.info("Listando todas as pessoas.");
		
		List<BookVO> persons = DozerMapper.parseObjectsList(repository.findAll(), BookVO.class);

		persons
			.stream()
			.forEach(p -> {
				try {
					p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		
		return persons;
		
	}

	
	public BookVO findById(Long id) throws Exception {
		
		logger.info("Procurando pessoa pelo ID.");

		Book bookAux = repository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;
		
		BookVO vo = DozerMapper.parseObject(bookAux, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}

	
	public BookVO create(BookVO book) throws Exception {
		
		if (book == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Inserindo uma pessoa.");
		
		Book bookAux = DozerMapper.parseObject(book, Book.class);
		
		BookVO vo = DozerMapper.parseObject(repository.save(bookAux), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;		
	}

	
	public BookVO update(BookVO book) throws Exception {

		if (book == null) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Atualizando uma pessoa.");
		
		Book bookBD = repository.findById(book.getKey())
							.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		bookBD.setAuthor(book.getAuthor());
		bookBD.setLaunchDate(book.getLaunchDate());
		bookBD.setPrice(book.getPrice());
		bookBD.setTitle(book.getTitle());

		BookVO vo = DozerMapper.parseObject(repository.save(bookBD), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
		return vo;

	}

	
	public void delete(Long id) {
		
		logger.info("Excluindo uma pessoa.");
		
		Book bookBD = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado.")) ;

		repository.delete(bookBD);
		
	}

}
