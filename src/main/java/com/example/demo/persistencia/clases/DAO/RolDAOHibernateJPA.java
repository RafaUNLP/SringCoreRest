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
        	return em.createQuery("SELECT r FROM Rol r WHERE UPPER(r.nombre) LIKE UPPER(:nombre)", this.entityClass)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Más de un Rol encontrado con el nombre: " + nombre);
        }
    }
}
