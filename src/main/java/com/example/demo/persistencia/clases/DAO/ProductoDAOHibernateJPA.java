package com.example.demo.persistencia.clases.DAO;

import java.util.List;

import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.demo.persistencia.clases.entidades.Producto;
import com.example.demo.persistencia.interfaces.ProductoDAO;

@Repository
public class ProductoDAOHibernateJPA extends GenericDAOHibernateJPA<Producto> implements ProductoDAO {

    public ProductoDAOHibernateJPA() {
        super(Producto.class);
    }

    @Override
    public List<Producto> findAllOrderedByName() {
        // Crear la consulta para obtener todos los productos ordenados por nombre
        TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p ORDER BY p.nombre", this.entityClass);
        return query.getResultList();
    }
}
