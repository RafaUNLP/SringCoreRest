package com.example.demo.persistencia.clases.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.persistencia.clases.entidades.Turno;
import com.example.demo.persistencia.interfaces.TurnoDAO;

@Repository
public class TurnoDAOHibernateJPA extends GenericDAOHibernateJPA<Turno> implements TurnoDAO {

    public TurnoDAOHibernateJPA() {
        super(Turno.class);
    }

    @Override
    public List<Turno> findAllOrderedByInitialHour() {
        return em.createQuery("SELECT t FROM Turno t ORDER BY t.horaEntrada ASC", Turno.class).getResultList();
    }
}
