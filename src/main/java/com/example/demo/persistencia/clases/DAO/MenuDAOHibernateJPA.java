package com.example.demo.persistencia.clases.DAO;

import java.util.List;

import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.demo.persistencia.clases.entidades.Menu;
import com.example.demo.persistencia.interfaces.MenuDAO;

@Repository
public class MenuDAOHibernateJPA extends GenericDAOHibernateJPA<Menu> implements MenuDAO {

    public MenuDAOHibernateJPA() {
        super(Menu.class);
    }

    @Override
    public List<Menu> findVegetarians() {
        TypedQuery<Menu> query = this.em.createQuery("FROM MenuVegetariano", this.entityClass);
        return query.getResultList();
    }

    @Override
    public List<Menu> findStandards() {

        TypedQuery<Menu> query = this.em.createQuery("FROM MenuEstandar", this.entityClass);
        return query.getResultList();
    }
}