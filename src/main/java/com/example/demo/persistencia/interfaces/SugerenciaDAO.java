package com.example.demo.persistencia.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.persistencia.clases.entidades.Sugerencia;

public interface SugerenciaDAO extends GenericDAO<Sugerencia>{

	/*metodo privado que retorna una query, uno usa esa query y el otro le agrega el limite*/
	public List<Sugerencia> findByDate (LocalDate fecha);
	public List<Sugerencia> findByDate (LocalDate fecha, int maxResult);
	public List<Sugerencia> findAllOrderedByDateAsc ();
	public List<Sugerencia> findAllOrderedByDateDesc ();
}
