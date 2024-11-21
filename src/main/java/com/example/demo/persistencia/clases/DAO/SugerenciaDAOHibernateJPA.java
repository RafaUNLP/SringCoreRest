package com.example.demo.persistencia.clases.DAO;

import com.example.demo.persistencia.clases.entidades.Sugerencia;
import com.example.demo.persistencia.interfaces.SugerenciaDAO;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SugerenciaDAOHibernateJPA extends GenericDAOHibernateJPA<Sugerencia> implements SugerenciaDAO {

    public SugerenciaDAOHibernateJPA() {
        super(Sugerencia.class);
    }

    // Helper method to simplify queries by date
    private TypedQuery<Sugerencia> findByDateHelper(LocalDate fecha) {
        return em.createQuery("SELECT s FROM Sugerencia s WHERE s.fecha = :fecha ORDER BY s.fecha DESC", this.entityClass)
                 .setParameter("fecha", fecha);
    }

    // Find suggestions by date
    @Override
    public List<Sugerencia> findByDate(LocalDate fecha) {
        return findByDateHelper(fecha).getResultList();
    }

    // Find suggestions by date with limit
    @Override
    public List<Sugerencia> findByDate(LocalDate fecha, int maxResult) {
        return findByDateHelper(fecha).setMaxResults(maxResult).getResultList();
    }

    // Find all suggestions ordered by date ascending
    @Override
    public List<Sugerencia> findAllOrderedByDateAsc() {
        return em.createQuery("SELECT s FROM Sugerencia s ORDER BY s.fecha ASC", this.entityClass)
                 .getResultList();
    }

    // Find all suggestions ordered by date descending
    @Override
    public List<Sugerencia> findAllOrderedByDateDesc() {
        return em.createQuery("SELECT s FROM Sugerencia s ORDER BY s.fecha DESC", this.entityClass)
                 .getResultList();
    }
}
