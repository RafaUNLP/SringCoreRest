package com.example.demo.persistencia.clases.DAO;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.interfaces.RolDAO;

@Repository
public class RolDAOHibernateJPA extends GenericDAOHibernateJPA<Rol> implements RolDAO {

    public RolDAOHibernateJPA() {
        super(Rol.class);
    }

    @Override
    public Rol findByName(String nombre) {
        try {
            // Realizar la consulta para encontrar el rol por nombre
            return em.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombre", this.entityClass)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Si no se encuentra un resultado, devolver null
            return null;
        } catch (NonUniqueResultException e) {
            // Si se encuentran más de un resultado, lanzar una excepción
            throw new IllegalStateException("Más de un rol encontrado con el nombre: " + nombre);
        }
    }
}
