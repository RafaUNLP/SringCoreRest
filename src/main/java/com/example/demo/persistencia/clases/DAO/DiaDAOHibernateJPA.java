package com.example.demo.persistencia.clases.DAO;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;

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
    public Dia persist(Dia dia) {
        try {
        	Dia previo = this.findByEnumDiaQuery(dia.getEnumDia());
        	if(previo != null)
        		throw new RuntimeException("Ya existe el día " + previo.getEnumDia());
            em.persist(dia); 
            return dia;
        } catch (RuntimeException e) {
            throw new PersistenceException("Error al persistir la entidad: ", e);
        }
    }
    
    @Override
    public Dia findByEnumDia(EnumDia enumerativo) {
        try {
            return this.findByEnumDiaQuery(enumerativo);
        } catch (NoResultException e) {
            throw new NoResultException("No se encontró una instancia de Dia con el enum " + enumerativo.toString());
        } catch (NonUniqueResultException e) {
            throw new NonUniqueResultException("Se encontró más de una instancia de Dia con el enum "
                    + enumerativo.toString() + ", lo cual no debe permitirse");
        } catch (Exception e) {
            throw e;
        }
    }
    
    private Dia findByEnumDiaQuery (EnumDia enumerativo) {
    	return em.createQuery("SELECT d FROM "+ this.entityClass.getName() +" d WHERE d.enumDia = :enumerativo", this.entityClass)
                .setParameter("enumerativo", enumerativo)
                .getSingleResult();
    }
}