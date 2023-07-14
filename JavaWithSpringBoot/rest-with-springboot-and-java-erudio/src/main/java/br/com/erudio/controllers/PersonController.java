package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

<<<<<<< HEAD
//@CrossOrigin(origins = "http://localhost:8080")	libera cross origin para o endereço especificado (especificar o endereço é opcional)
=======
>>>>>>> main
@RestController
@RequestMapping("/person/v1")
@Tag(name = "Person", description = "Endpoints para gerenciar pessoas.")
public class PersonController {

	@Autowired
	PersonServices personService;


	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Find All", 
			   description = "Recupera todas as pessoas cadastradas.",
			   tags = {"Person"},
			   responses = {
					   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
							   @Content(
									   mediaType = "application/json",
									   array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
									   )							   
					   }),					   
					   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
					   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
					   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
					   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
			   })
	public List<PersonVO> findByAll() throws Exception {

		return personService.findAll();
		
	}


	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Find By Id", 
	   description = "Recupera pessoa pelo ID.",
	   tags = {"Person"},
	   responses = {
			   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
					   @Content(
							   mediaType = "application/json",
							   schema = @Schema(implementation = PersonVO.class)
							   )							   
			   }),					   
			   @ApiResponse(description = "No Content", responseCode = "204", content = @Content),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {

		return personService.findById(id);
		
	}


	@CrossOrigin(origins = {"http://localhost:8080", "http://erudio.com.br"})
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
			     produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Create a person", 
	   description = "Cria uma pessoa recebendo um objeto PersonVO em Json, XML ou YML.",
	   tags = {"Person"},
	   responses = {
			   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
					   @Content(
							   mediaType = "application/json",
							   schema = @Schema(implementation = PersonVO.class)
							   )							   
			   }),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public PersonVO create(@RequestBody PersonVO person) throws Exception {
		
		return personService.create(person);
		
	}


	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
			    produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Update a person", 
	   description = "Atualiza uma pessoa no banco de dados recebendo um objeto PersonVO em Json, XML ou YML.",
	   tags = {"Person"},
	   responses = {
			   @ApiResponse(description = "Sucesso", responseCode = "200", content = {
					   @Content(
							   mediaType = "application/json",
							   schema = @Schema(implementation = PersonVO.class)
							   )							   
			   }),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public PersonVO update(@RequestBody PersonVO person) throws Exception {
		
		return personService.update(person);
		
	}

	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a person", 
	   description = "Exclui uma pessoa pelo ID.",
	   tags = {"Person"},
	   responses = {
			   @ApiResponse(description = "No Content", responseCode = "204", content = @Content),					   
			   @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),					   
			   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),				   
			   @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),					   
			   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)					   
	   })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

		personService.delete(id);
		return ResponseEntity.noContent().build();
		
	}


}
