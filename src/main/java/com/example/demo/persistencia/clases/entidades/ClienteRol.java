package com.example.demo.persistencia.clases.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("cliente")
public class ClienteRol extends Rol {

	@Override
	public boolean puedeSugerir() {
		return true;
	}

	@Override
	public boolean puedeComprar() {
		return true;
	}

	@Override
	public boolean puedeTernerTurnos() {
		return false;
	}

}
