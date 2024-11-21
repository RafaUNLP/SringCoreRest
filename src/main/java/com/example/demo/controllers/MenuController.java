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

import com.example.demo.persistencia.clases.DAO.MenuDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Menu;
import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
	
	@Autowired
	private MenuDAOHibernateJPA menuDAO;
	
	@PostMapping("/createMenu")
	public ResponseEntity<Menu> createMenu(@RequestBody Menu menu){
		try {
			Menu menuPersistido = menuDAO.persist(menu);
			return new ResponseEntity<>(menuPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	@PutMapping("/updateMenu")
	public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu){
		try {
			menuDAO.update(menu);
			return new ResponseEntity<Menu>(menu, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Menu>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteMenuById")
	public ResponseEntity<Menu> deleteMenuById(@RequestBody long id){
		try {
			menuDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Menu>(HttpStatus.NO_CONTENT);	
		}
	}
	@DeleteMapping("/deleteMenu")
	public ResponseEntity<Menu> deleteMenu(@RequestBody Menu menu){
		try {
			menuDAO.delete(menu);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Menu>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getMenuById/{id}")
	public ResponseEntity<Menu> getMenuById(@PathVariable long id){
		try {
			Menu menu= menuDAO.findById(id);
			return new ResponseEntity<Menu>(menu, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getMenus")
	public ResponseEntity<List<Menu>> getMenus(){		
		try {
			List<Menu> menus= menuDAO.findAll();
			return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Menu>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("/getMenusVegetarians")
	public ResponseEntity<List<Menu>> getMenusVegetarians(){		
		try {
			List<Menu> menus= menuDAO.findVegetarians();
			return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Menu>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("/getMenusStandars")
	public ResponseEntity<List<Menu>> getMenusStandars(){		
		try {
			List<Menu> menus= menuDAO.findStandards();
			return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Menu>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	
}
