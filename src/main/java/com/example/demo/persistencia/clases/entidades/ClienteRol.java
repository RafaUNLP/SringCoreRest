package com.example.demo.persistencia.clases.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
	public boolean puedeTenerTurnos() {
		return false;
	}

}
