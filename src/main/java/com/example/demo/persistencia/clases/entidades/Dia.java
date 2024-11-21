package com.example.demo.persistencia.clases.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Dia extends EntidadBase{

	@NotNull @Enumerated(EnumType.STRING)
	private EnumDia enumDia;
	
	@OneToOne
    @JoinColumn(name = "menu_vegetariano_id", referencedColumnName = "id")
	private MenuVegetariano menuVegenariano;
	
	@OneToOne
    @JoinColumn(name = "menu_estandar_id", referencedColumnName = "id")
	private MenuEstandar menuEstandar;
	
	public Dia() {} //Hibernate y POJOs

	public Dia(EnumDia enumDia) {
		this.enumDia = enumDia;
	}

	public MenuVegetariano getMenuVegenariano() {
		return menuVegenariano;
	}

	public void setMenuVegenariano(MenuVegetariano menuVegenariano) {
		this.menuVegenariano = menuVegenariano;
	}

	public MenuEstandar getMenuEstandar() {
		return menuEstandar;
	}

	public void setMenuEstandar(MenuEstandar menuEstandar) {
		this.menuEstandar = menuEstandar;
	}

	public EnumDia getEnumDia() {
		return enumDia;
	}
	
	
	
	
}
