package com.example.demo.persistencia.interfaces;

import java.time.LocalDate;
import java.util.List;
import com.example.demo.persistencia.clases.entidades.Compra;

public interface CompraDAO extends GenericDAO<Compra> {

	/*Se implementan con un metodo privado que retorna la QUERY, uno la va a usar asi y la otra va a ponerle un max result*/
	public List<Compra> findBetweenDates (LocalDate inicio, LocalDate fin);
	public List<Compra> findBetweenDates (LocalDate inicio, LocalDate fin, int maxResultados);
}
