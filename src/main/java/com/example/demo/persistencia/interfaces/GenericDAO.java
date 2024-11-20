package com.example.demo.persistencia.interfaces;

import java.util.List;

public interface GenericDAO<T> {

	public T persist (T generic);
	public boolean exist (long id);
	public T findById (long id);
	public List<T> findAll();
	public T update(T generic);
	public void delete (T generic);
	public boolean delete (long id);
	
}
