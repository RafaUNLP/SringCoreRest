package com.example.demo.persistencia.clases.DAO;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Usuario;
import com.example.demo.persistencia.interfaces.UsuarioDAO;

import java.util.List;

@Repository
public class UsuarioDAOHibernateJPA extends GenericDAOHibernateJPA<Usuario> implements UsuarioDAO {

    public UsuarioDAOHibernateJPA() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByDni(String dni) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni", this.entityClass)
                     .setParameter("dni", dni)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Puedes retornar null si no se encuentra el usuario
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Más de un usuario encontrado con el DNI: " + dni);
        }
    }

    @Override
    public List<Usuario> findByRol(Rol rol) {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.rol.nombre = :rol ORDER BY u.nombre ASC", this.entityClass);
        query.setParameter("rol", rol.getNombre());
        return query.getResultList();  // El ciclo de vida del EntityManager es manejado por Spring
    }
    
    @Override
    public List<Usuario> findAllOrderedByNameAsc() {
        TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u ORDER BY u.nombre ASC", this.entityClass);
        return query.getResultList();  // El ciclo de vida del EntityManager es manejado por Spring
    }
}
