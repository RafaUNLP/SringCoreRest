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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos/")
@Tag(name="Productos", description="CRUD de productos")
public class ProductoController {
	@Autowired
	private ProductoDAOHibernateJPA productoDAO;

	@PostMapping()
	@Operation(summary="Crear un producto")
	public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto){
		try {
			Producto productoPersistido = productoDAO.persist(producto);
			return new ResponseEntity<>(productoPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar un producto")
	public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto){
		try {
			productoDAO.update(producto);
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Eliminar un producto por su Id")
	public ResponseEntity<Producto> deleteProductoById(@PathVariable long id){
		try {
			productoDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);	
		}
	}
	@DeleteMapping()
	@Operation(summary="Eliminar un producto")
	public ResponseEntity<Producto> deleteProducto(@RequestBody Producto producto ){
		try {
			productoDAO.delete(producto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("{id}")
	@Operation(summary="Recuperar un producto por su Id")
	public ResponseEntity<Producto> getProductoById(@PathVariable long id){
		try {
			Producto producto= productoDAO.findById(id);
			if(producto == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping()
	@Operation(summary="Recuperar todos los productos")
	public ResponseEntity<List<Producto>> getProductos(){		
		try {
			List<Producto> producto = productoDAO.findAll();
			return new ResponseEntity<List<Producto>>(producto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	@GetMapping("/ordenados-por-nombre")
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
