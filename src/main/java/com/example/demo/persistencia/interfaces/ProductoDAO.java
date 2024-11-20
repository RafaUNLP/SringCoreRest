package com.example.demo.persistencia.interfaces;

import java.util.List;
import com.example.demo.persistencia.clases.entidades.Producto;

public interface ProductoDAO extends GenericDAO<Producto>{

	public List<Producto> findAllOrderedByName ();
}
