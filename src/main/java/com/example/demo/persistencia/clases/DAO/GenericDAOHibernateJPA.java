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
            em.persist(entity);
            em.flush();
            return entity;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al persistir la entidad " + this.entityClass.getSimpleName(), e);
        }
    }

    @Override
    public boolean exist(long id) {
        try {
            // Consulta optimizada con "SELECT 1", que es más eficiente
            Integer count = em.createQuery("SELECT 1 FROM " + entityClass.getName() + " e WHERE e.id = :id", Integer.class)
                           .setParameter("id", id)
                           .getSingleResult();
            return count != null;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al verificar la existencia de la entidad" + this.entityClass.getSimpleName(), e);
        }
    }

    @Override
    public T findById(long id) {
        try {
            return em.find(entityClass, id);
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al buscar la entidad" + this.entityClass.getSimpleName() + "por ID", e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al obtener todos las entidades de " + this.entityClass.getSimpleName(), e);
        }
    }

    @Override
    public T update(T entity) {
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al actualizar la entidad" + this.entityClass.getSimpleName(), e);
        }
    }

    @Override
    public void delete(T entity) {
        try {
            em.remove(em.merge(entity));
            em.flush();
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al eliminar la entidad" + this.entityClass.getSimpleName(), e);
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al eliminar la entidad" + this.entityClass.getSimpleName() + " por ID", e);
        }
    }
}
