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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistencia.clases.DAO.CompraDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Compra;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compras/")
@Tag(name="Compras", description="CRUD de compras")
public class CompraController {
	
	@Autowired
	private CompraDAOHibernateJPA compraDAO;
	
	@PostMapping()
	@Operation(summary="Crear una compra")
	public ResponseEntity<Compra> createCompra(@Valid @RequestBody Compra compra){
		try {
			Compra compraPersistida = compraDAO.persist(compra);
			return new ResponseEntity<>(compraPersistida, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar una compra")
	public ResponseEntity<Compra> updateCompra(@Valid @RequestBody Compra compra){
		try {
			compra = compraDAO.update(compra);
			return new ResponseEntity<Compra>(compra, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Eliminar una compra por su Id")
	public ResponseEntity<Compra> deleteCompraById(@PathVariable long id){
		try {
			compraDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping()
	@Operation(summary="Eliminar una compra")
	public ResponseEntity<Compra> deleteCompra(@RequestBody Compra compra){
		try {
			compraDAO.delete(compra);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("{id}")
	@Operation(summary="Recuperar una compra por su Id")
	public ResponseEntity<Compra> getCompraById(@PathVariable long id){
		try {
			Compra compra = compraDAO.findById(id);
			if(compra == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Compra>(compra, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping()
	@Operation(summary="Recuperar todas las compras")
	public ResponseEntity<List<Compra>> getCompras(){		
		try {
			List<Compra> compras = compraDAO.findAll();
			return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Compra>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("between-dates")
	@Operation(summary="Recuperar las compras entre dos fechas, puediendo optar por un máximo")
	public ResponseEntity<List<Compra>> getCompras(@RequestBody int max, @RequestBody LocalDate fechaInicio, @RequestBody LocalDate fechaFin ){		
		try {
			if (max > 0) {				
				List<Compra> compras = compraDAO.findBetweenDates(fechaInicio, fechaFin, max);
				return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
			}else {
				List<Compra> compras = compraDAO.findBetweenDates(fechaInicio, fechaFin);
				return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
			}
			
		}catch(Exception e) {
			return new ResponseEntity<List<Compra>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	
	
	
}
