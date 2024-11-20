package com.example.demo.persistencia.interfaces;

import java.util.List;

import com.example.demo.persistencia.clases.entidades.Menu;

public interface MenuDAO extends GenericDAO<Menu>{
	
	public List<Menu> findVegetarians ();
	public List<Menu> findStandards ();
}
