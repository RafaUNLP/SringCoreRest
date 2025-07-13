package com.example.demo.controllers;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistencia.clases.DAO.SugerenciaDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Sugerencia;
import com.example.demo.persistencia.clases.entidades.Usuario;
import com.example.demo.persistencia.clases.DAO.UsuarioDAOHibernateJPA;
import com.example.demo.persistencia.clases.DTO.SugerenciaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sugerencias/")
@Tag(name="Sugerencias", description="CRUD de sugerencias de los usuarios")
public class SugerenciaController {
	
	@Autowired
	private SugerenciaDAOHibernateJPA sugerenciaDAO;
	
	@Autowired
	private UsuarioDAOHibernateJPA usuarioDAO;
	
	@PostMapping()
	@Operation(summary="Crear una sugerencia")
	public ResponseEntity<SugerenciaDTO> createSugerencia(@Valid @RequestBody SugerenciaDTO sugerenciaDTO){
		try {
			Usuario autor = usuarioDAO.findById(sugerenciaDTO.getUsuarioId());
			if(autor == null) {
				throw new EntityNotFoundException();
			}
			Sugerencia sugerencia = new Sugerencia(sugerenciaDTO.getTexto(),sugerenciaDTO.getFecha(),sugerenciaDTO.getCategoria(),autor);
			if(autor.addSugerencia(sugerencia)){
				sugerencia = sugerenciaDAO.persist(sugerencia);
				autor = usuarioDAO.update(autor);
				sugerenciaDTO.setUsuarioId(autor.getId());
				sugerenciaDTO.setId(sugerencia.getId());
				sugerenciaDTO.setNombreAutor(autor.getNombre() + " " + autor.getApellido());
				return new ResponseEntity<>(sugerenciaDTO, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar una sugerencia")
	public ResponseEntity<SugerenciaDTO> updateUsuario(@Valid @RequestBody SugerenciaDTO sugerencia){
		try {
			Sugerencia original = sugerenciaDAO.findById(sugerencia.getId());
			if(original == null)
				throw new EntityNotFoundException("No se encontró la sugerencia a modificar");
			original.setCategoria(sugerencia.getCategoria());
			original.setTexto(sugerencia.getTexto());
			original.setFecha(sugerencia.getFecha());
			original = sugerenciaDAO.update(original);
			return new ResponseEntity<SugerenciaDTO>(sugerencia, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Eliminar una sugerencia por su Id")
	public ResponseEntity<Sugerencia> deleteSugerenciaById(@PathVariable long id){
		try {
			sugerenciaDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Sugerencia>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping()
	@Operation(summary="Eliminar una sugerencia")
	public ResponseEntity<Sugerencia> deleteSugerencia(@RequestBody Sugerencia sugerencia){
		try {
			sugerenciaDAO.delete(sugerencia);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Sugerencia>(HttpStatus.NO_CONTENT);	
		}
	}	
	
	
	@GetMapping()
	@Operation(summary="Recuperar todas las sugerencias")
	public ResponseEntity<List<SugerenciaDTO>> getSugerencia(){
		try {
			List<Sugerencia> sugerencias = sugerenciaDAO.findAll();
			List<SugerenciaDTO> dtos = sugerencias.stream().map(s -> new SugerenciaDTO(s)).toList();
			return new ResponseEntity<List<SugerenciaDTO>>(dtos, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<SugerenciaDTO>>(HttpStatus.NO_CONTENT);
		}
	}	
	
	@GetMapping("{id}")
	@Operation(summary="Recuperar una sugerencia por su Id")
	public ResponseEntity<SugerenciaDTO> getSugerenciaById(@PathVariable long id){
		try {
			Sugerencia sugerencia = sugerenciaDAO.findById(id);
			if(sugerencia == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<SugerenciaDTO>(new SugerenciaDTO(sugerencia), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/de-una-fecha/{date}/{max}")
	@Operation(summary="Recuperar todas las sugerencias de una fecha")
	public ResponseEntity<List<SugerenciaDTO>> getSugerenciasByDate(@PathVariable LocalDate date, @PathVariable int max){
		try {
			if(max < 1)
				max = Integer.MAX_VALUE;
			List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(date, max);
			List<SugerenciaDTO> dtos = sugerencias.stream().map(s -> new SugerenciaDTO(s)).toList();
			return new ResponseEntity<List<SugerenciaDTO>>(dtos, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<SugerenciaDTO>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("ordenadas-por-fecha/{orden}")
	@Operation(summary="Recuperar todas las sugerencias ordenadas por fecha ('ascendente' o 'descendente')")
	public ResponseEntity<List<SugerenciaDTO>> getSugerenciasOrdereredByDate(@PathVariable String orden){
	    try {
	        List<Sugerencia> sugerencias;

	        if ("ascendente".equalsIgnoreCase(orden)) {
	            sugerencias = sugerenciaDAO.findAllOrderedByDateAsc();
	        } else if ("descendente".equalsIgnoreCase(orden)) {
	            sugerencias = sugerenciaDAO.findAllOrderedByDateDesc();
	        } else {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        List<SugerenciaDTO> dtos = sugerencias.stream().map(s -> new SugerenciaDTO(s)).toList();
	        return new ResponseEntity<List<SugerenciaDTO>>(dtos, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	}
	
	

}
