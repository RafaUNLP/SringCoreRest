package com.example.demo.persistencia.interfaces;

import com.example.demo.persistencia.clases.entidades.Rol;

public interface RolDAO extends GenericDAO<Rol> {

	public Rol findByName(String nombre);
}
