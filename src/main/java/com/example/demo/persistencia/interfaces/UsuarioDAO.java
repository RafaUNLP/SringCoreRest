package com.example.demo.persistencia.interfaces;

import java.util.List;
import com.example.demo.persistencia.clases.entidades.Usuario;

import jakarta.persistence.PersistenceContext;

import com.example.demo.persistencia.clases.entidades.Rol;

@PersistenceContext
public interface UsuarioDAO extends GenericDAO<Usuario>{

	public Usuario findByDni (String dni);
	public List<Usuario> findByRol(Rol rol);
	List<Usuario> findAllOrderedByNameAsc();
	
}
