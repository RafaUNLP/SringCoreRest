package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.persistencia.clases.DAO.MenuDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;


@RestController
@RequestMapping("/api/menus/")
@Tag(name="Menus", description="CRUD de menus")
public class MenuController {
	
	@Autowired
	private MenuDAOHibernateJPA menuDAO;
	
	@PostMapping()
	@Operation(summary="Crear un menu")
	public ResponseEntity<Menu> createMenu(@RequestBody Menu menu){
		try {
			Menu menuPersistido = menuDAO.persist(menu);
			return new ResponseEntity<>(menuPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	@PutMapping()
	@Operation(summary="Actualizar un menu")
	public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu){
		try {
			menuDAO.update(menu);
			return new ResponseEntity<Menu>(menu, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Menu>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Eliminar un menu por su Id")
	public ResponseEntity<Menu> deleteMenuById(@PathVariable long id){
		try {
			menuDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Menu>(HttpStatus.NO_CONTENT);	
		}
	}
	@DeleteMapping()
	@Operation(summary="Eliminar un menu")
	public ResponseEntity<Menu> deleteMenu(@RequestBody Menu menu){
		try {
			menuDAO.delete(menu);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Menu>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("{id}")
	@Operation(summary="Recuperar un menu por su Id")
	public ResponseEntity<Menu> getMenuById(@PathVariable long id){
		try {
			Menu menu= menuDAO.findById(id);
			if(menu == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
			return new ResponseEntity<Menu>(menu, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping()
	@Operation(summary="Recuperar todos los menus")
	public ResponseEntity<List<Menu>> getMenus(){		
		try {
			List<Menu> menus= menuDAO.findAll();
			return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Menu>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("vegetarianos")
	@Operation(summary="Recuperar los menus vegetarianos")
	public ResponseEntity<List<Menu>> getMenusVegetarians(){		
		try {
			List<Menu> menus= menuDAO.findVegetarians();
			return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Menu>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("estandar")
	@Operation(summary="Recuperar los menus estandar")
	public ResponseEntity<List<Menu>> getMenusStandars(){		
		try {
			List<Menu> menus= menuDAO.findStandards();
			return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Menu>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	
}
