package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book/v1")
@Tag(name = "Book", description = "Endpoints para gerenciar livros.")
public class BookController {

	@Autowired
	BookServices bookServices;


	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Find All", 
			   description = "Recupera todos os livros cadastradas.",
			   tags = {"Book"},
			   responses = {
					   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
							   @Content(
									   mediaType = "application/json",
									   array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
									   )							   
					   }),					   
					   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
					   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
					   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
					   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
			   })
	public List<BookVO> findByAll() throws Exception {

		return bookServices.findAll();
		
	}


	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Find By Id", 
	   description = "Recupera um livor pelo ID.",
	   tags = {"Book"},
	   responses = {
			   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
					   @Content(
							   mediaType = "application/json",
							   schema = @Schema(implementation = BookVO.class)
							   )							   
			   }),					   
			   @ApiResponse(description = "No Content", responseCode = "204", content = @Content),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public BookVO findById(@PathVariable(value = "id") Long id) throws Exception {

		return bookServices.findById(id);
		
	}


	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
			     produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Create a book", 
	   description = "Cria um livro recebendo um objeto BookVO em Json, XML ou YML.",
	   tags = {"Book"},
	   responses = {
			   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
					   @Content(
							   mediaType = "application/json",
							   schema = @Schema(implementation = BookVO.class)
							   )							   
			   }),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public BookVO create(@RequestBody BookVO book) throws Exception {
		
		return bookServices.create(book);
		
	}


	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
			    produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Update a book", 
	   description = "Atualiza um livro no banco de dados recebendo um objeto BookVO em Json, XML ou YML.",
	   tags = {"Book"},
	   responses = {
			   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
					   @Content(
							   mediaType = "application/json",
							   schema = @Schema(implementation = BookVO.class)
							   )							   
			   }),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public BookVO update(@RequestBody BookVO book) throws Exception {
		
		return bookServices.update(book);
		
	}

	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a book", 
	   description = "Exclui um livro pelo ID.",
	   tags = {"Book"},
	   responses = {
			   @ApiResponse(description = "No Content", responseCode = "204", content = @Content),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

		bookServices.delete(id);
		return ResponseEntity.noContent().build();
		
	}


}
