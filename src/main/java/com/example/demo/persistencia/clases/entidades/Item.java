package com.example.demo.persistencia.clases.entidades;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME, 
	    include = JsonTypeInfo.As.PROPERTY, 
	    property = "type"
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = Producto.class, name = "producto"),
	    @JsonSubTypes.Type(value = Menu.class, name = "menu")
	})
public abstract class Item extends EntidadBase{

	 /*Esta clase inicialmente era una interfaz, pero como Hibernate no las persiste, me vi obligado a hacerlo 
	 * una clase abstracta para que una Compra pueda tener una lista polimórfica de Items.
	 */
	
	public Item () {}
	
	public abstract String getNombre();
	public abstract void setNombre (String nombre);
	
	public abstract double getPrecio();
	public abstract void setPrecio (double precio);
}

