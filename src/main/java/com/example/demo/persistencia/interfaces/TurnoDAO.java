package com.example.demo.persistencia.interfaces;

import java.util.List;

import com.example.demo.persistencia.clases.entidades.Turno;

public interface TurnoDAO extends GenericDAO<Turno> {

	public List<Turno> findAllOrderedByInitialHour();
}
