package com.example.demo.persistencia.clases.entidades;

import jakarta.persistence.Entity;

@Entity
//NOTA: su Id es tambien la FK a la tabla Menu
public class MenuEstandar extends Menu {

	/*usamos MenuEstandar y MenuVegetariano para forzar mediante el tipado que
	 * un dia tenga si o si un menu de cada tipo*/
	
	public MenuEstandar () { super();}
	
	public MenuEstandar(double precio, String nombre, String entrada, String platoPrincipal, String postre,String bebida) {
		super(precio, nombre, entrada, platoPrincipal, postre, bebida);
	}

	@Override
	public boolean esVegetariano() {
		return false;
	}
}
