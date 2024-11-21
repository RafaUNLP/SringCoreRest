package com.example.demo.persistencia.clases.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.demo.persistencia.clases.entidades.Dia;
import com.example.demo.persistencia.clases.entidades.EnumDia;
import com.example.demo.persistencia.interfaces.DiaDAO;

@Repository
public class DiaDAOHibernateJPA extends GenericDAOHibernateJPA<Dia> implements DiaDAO {

    public DiaDAOHibernateJPA() {
        super(Dia.class);
    }

    @Override
    public Dia findByEnumDia(EnumDia enumerativo) {
        try {
            return em.createQuery("SELECT d FROM Dia d WHERE d.enumDia = :enumerativo", this.entityClass)
                     .setParameter("enumerativo", enumerativo)
                     .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("No se encontró una instancia de Dia con el enum " + enumerativo.toString());
        } catch (NonUniqueResultException e) {
            throw new NonUniqueResultException("Se encontró más de una instancia de Dia con el enum "
                    + enumerativo.toString() + ", lo cual no debe permitirse");
        } catch (Exception e) {
            throw e;  // Se puede lanzar la excepción original
        }
    }
}