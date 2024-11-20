package com.example.demo.persistencia.clases.entidades;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Menu extends Item{

	@NotNull @DecimalMin(value = "0.0", message = "El precio debe ser al menos 0.0")
    @DecimalMax(value = "99999999.9", message = "El precio no debe ser mayor que 99.999.999,9")
	private double precio;
	
	@NotNull @Size(max=50,message="El nombre no debe superar los 50 caracteres")
	private String nombre;
	
	@NotNull @Size(max=100,message="La entrada no debe superar los 100 caracteres")
	private String entrada;
	
	@NotNull @Size(max=100,message="El plato principal no debe superar los 100 caracteres")
	private String platoPrincipal;
	
	@NotNull @Size(max=100,message="El postre no debe superar los 100 caracteres")
	private String postre;
	
	@NotNull @Size(max=50,message="La bebida no debe superar los 50 caracteres")
	private String bebida;

	public Menu() {}
	
	public Menu( double precio,String nombre, String entrada, String platoPrincipal, String postre, String bebida) {
		super();
		this.precio = precio;
		this.nombre = nombre;
		this.entrada = entrada;
		this.platoPrincipal = platoPrincipal;
		this.postre = postre;
		this.bebida = bebida;
	}

	public abstract boolean esVegetariano();
	
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getPlatoPrincipal() {
		return platoPrincipal;
	}

	public void setPlatoPrincipal(String platoPrincipal) {
		this.platoPrincipal = platoPrincipal;
	}

	public String getPostre() {
		return postre;
	}

	public void setPostre(String postre) {
		this.postre = postre;
	}

	public String getBebida() {
		return bebida;
	}

	public void setBebida(String bebida) {
		this.bebida = bebida;
	}

}
