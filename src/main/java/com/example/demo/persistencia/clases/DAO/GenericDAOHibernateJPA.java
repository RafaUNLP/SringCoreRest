package com.example.demo.persistencia.clases.DAO;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import com.example.demo.persistencia.interfaces.GenericDAO;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class GenericDAOHibernateJPA<T> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> entityClass;

    public GenericDAOHibernateJPA(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T persist(T entity) {
        try {
            em.merge(entity);
            return entity;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al persistir la entidad", e);
        }
    }

    @Override
    public boolean exist(long id) {
        try {
            // Consulta optimizada con "SELECT 1", que es más eficiente
            Long count = em.createQuery("SELECT 1 FROM " + entityClass.getName() + " e WHERE e.id = :id", Long.class)
                           .setParameter("id", id)
                           .getSingleResult();
            return count != null;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al verificar la existencia de la entidad", e);
        }
    }

    @Override
    public T findById(long id) {
        try {
            return em.find(entityClass, id);  // Usamos find para buscar la entidad
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al buscar la entidad por ID", e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            // Consulta para obtener todas las entidades
            return em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al obtener todas las entidades", e);
        }
    }

    @Override
    public T update(T entity) {
        try {
            return em.merge(entity);  // Usamos merge para actualizar la entidad
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al actualizar la entidad", e);
        }
    }

    @Override
    public void delete(T entity) {
        try {
            em.remove(em.merge(entity));  // Si la entidad está gestionada, no es necesario hacer merge
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al eliminar la entidad", e);
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            T entity = em.find(entityClass, id);  // Buscamos la entidad por ID
            if (entity != null) {
                em.remove(entity);  // Eliminamos la entidad si se encuentra
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al eliminar la entidad por ID", e);
        }
    }
}
