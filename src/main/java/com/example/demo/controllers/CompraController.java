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


import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/compra")
public class CompraController {
	
	@Autowired
	private CompraDAOHibernateJPA compraDAO;
	
	@PostMapping("/createCompra")
	public ResponseEntity<Compra> createCompra(@RequestBody Compra compra){
		try {
			Compra compraPersistida = compraDAO.persist(compra);
			return new ResponseEntity<>(compraPersistida, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping("/updateCompra")
	public ResponseEntity<Compra> updateCompra(@RequestBody Compra compra){
		try {
			compraDAO.update(compra);
			return new ResponseEntity<Compra>(compra, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteCompraById")
	public ResponseEntity<Compra> deleteCompraById(@RequestBody long id){
		try {
			compraDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteCompra")
	public ResponseEntity<Compra> deleteUsuario(@RequestBody Compra compra){
		try {
			compraDAO.delete(compra);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Compra>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getCompraById/{id}")
	public ResponseEntity<Compra> getCompraById(@PathVariable long id){
		try {
			Compra compra = compraDAO.findById(id);
			return new ResponseEntity<Compra>(compra, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getCompras")
	public ResponseEntity<List<Compra>> getCompras(){		
		try {
			List<Compra> compras = compraDAO.findAll();
			return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Compra>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("/getComprasBetweenDates")
	public ResponseEntity<List<Compra>> getCompras(@RequestBody int max, @RequestBody LocalDate fechaInicio, @RequestBody LocalDate fechaFin ){		
		try {
			if (max == 0) {				
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
