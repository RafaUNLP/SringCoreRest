package com.example.demo.persistencia.clases.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
	public boolean puedeTenerTurnos() {
		return false;
	}

}
