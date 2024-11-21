package com.example.demo.controllers;

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

import com.example.demo.persistencia.clases.DAO.ProductoDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Producto;

import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {
	@Autowired
	private ProductoDAOHibernateJPA productoDAO;

	@PostMapping("/createProducto")
	public ResponseEntity<Producto> createProducto(@RequestBody Producto producto){
		try {
			Producto productoPersistido = productoDAO.persist(producto);
			return new ResponseEntity<>(productoPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping("/updateProducto")
	public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto){
		try {
			productoDAO.update(producto);
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteProductoById/{id}")
	public ResponseEntity<Producto> deleteProductoById(@PathVariable long id){
		try {
			productoDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);	
		}
	}
	@DeleteMapping("/deleteProducto")
	public ResponseEntity<Producto> deleteProducto(@RequestBody Producto producto ){
		try {
			productoDAO.delete(producto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getProductoById/{id}")
	public ResponseEntity<Producto> getProductoById(@PathVariable long id){
		try {
			Producto producto= productoDAO.findById(id);
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getProductos")
	public ResponseEntity<List<Producto>> getProductos(){		
		try {
			List<Producto> producto = productoDAO.findAll();
			return new ResponseEntity<List<Producto>>(producto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	@GetMapping("/getProductosOrderedByName")
	public ResponseEntity<List<Producto>> getProductosOrderedByNameAsc(){
		try {
			List<Producto> productos= productoDAO.findAllOrderedByName();
			return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Producto>> (HttpStatus.NO_CONTENT);	
		}
	}
	
	
	
	
}
