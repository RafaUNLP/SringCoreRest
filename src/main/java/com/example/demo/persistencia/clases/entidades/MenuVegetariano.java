package com.example.demo.persistencia.clases.entidades;

import javax.persistence.Entity;

@Entity
//NOTA: su Id es tambien la FK a la tabla Menu
public class MenuVegetariano extends Menu {

	/*usamos MenuVegetariano y MenuEstandar para forzar mediante el tipado que
	 * un dia tenga si o si un menu de cada tipo*/
	
	public MenuVegetariano () { super();}
	
	public MenuVegetariano(double precio, String nombre, String entrada, String platoPrincipal, String postre, String bebida) {
		super(precio, nombre, entrada, platoPrincipal, postre, bebida);
	}

	@Override
	public boolean esVegetariano() {
		return true;
	}
}
