package com.example.demo.persistencia.clases.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

import com.example.demo.persistencia.clases.entidades.Compra;
import com.example.demo.persistencia.interfaces.CompraDAO;

@Repository
public class CompraDAOHibernateJPA extends GenericDAOHibernateJPA<Compra> implements CompraDAO {

    @PersistenceContext
    private EntityManager em;

    public CompraDAOHibernateJPA() {
        super(Compra.class);
    }

    private TypedQuery<Compra> findBetweenHelper(LocalDate inicio, LocalDate fin) {
        return em.createQuery("SELECT c FROM Compra c WHERE c.fecha >= :inicio AND c.fecha <= :fin", this.entityClass)
                 .setParameter("inicio", inicio)
                 .setParameter("fin", fin);
    }

    @Override
    public List<Compra> findBetweenDates(LocalDate inicio, LocalDate fin) {
        return this.findBetweenHelper(inicio, fin).getResultList();
    }

    @Override
    public List<Compra> findBetweenDates(LocalDate inicio, LocalDate fin, int maxResultados) {
        return this.findBetweenHelper(inicio, fin).setMaxResults(maxResultados).getResultList();
    }
}