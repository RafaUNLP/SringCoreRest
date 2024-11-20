package com.example.demo.persistencia.clases.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("administrador")
public class AdministradorRol extends Rol {

	@Override
	public boolean puedeSugerir() {
		return false;
	}

	@Override
	public boolean puedeComprar() {
		return false;
	}

	@Override
	public boolean puedeTernerTurnos() {
		return false;
	}

}
