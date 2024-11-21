package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.persistencia.clases.DAO.CompraDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Compra;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")  // Activa el perfil 'test' para las pruebas
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCompraDAOHibernateJPA {

    @Autowired
    private CompraDAOHibernateJPA compraDao;

    private Compra compra1, compra2, compra3;

    @BeforeEach
    public void setUp() throws Exception {
        // Creamos algunas compras de ejemplo. No es necesario manejar transacciones manualmente.
        compra1 = new Compra(500.0, LocalDate.of(2024, 1, 15));
        compra2 = new Compra(1000.0, LocalDate.of(2024, 2, 20));
        compra3 = new Compra(1500.0, LocalDate.of(2024, 3, 10));

        // Persistimos las compras, Spring manejará la transacción automáticamente
        compraDao.persist(compra1);
        compraDao.persist(compra2);
        compraDao.persist(compra3);
    }

    @Test
    public void testFindBetweenDates() {
        // Caso vacío
        LocalDate inicio = LocalDate.of(1999, 1, 1);
        LocalDate fin = LocalDate.of(1999, 2, 28);
        List<Compra> compras = compraDao.findBetweenDates(inicio, fin);
        assertNotNull(compras);
        assertTrue(compras.isEmpty());  // Mejor que comparar tamaño con 0

        // Caso con resultados (hay 3, debería devolver 2)
        inicio = LocalDate.of(2024, 1, 1);
        fin = LocalDate.of(2024, 2, 28);
        compras = compraDao.findBetweenDates(inicio, fin);
        assertEquals(2, compras.size());
        assertEquals(500.0, compras.get(0).getPrecio());
        assertEquals(1000.0, compras.get(1).getPrecio());
    }

    @Test
    public void testFindBetweenDatesWithMaxResults() {
        // Caso vacío
        LocalDate inicio = LocalDate.of(1999, 1, 1);
        LocalDate fin = LocalDate.of(1999, 2, 28);
        List<Compra> compras = compraDao.findBetweenDates(inicio, fin, 1);
        assertNotNull(compras);
        assertTrue(compras.isEmpty());

        // Caso donde debería devolver 2 elementos pero se limita a 3
        inicio = LocalDate.of(2024, 1, 1);
        fin = LocalDate.of(2024, 3, 1);
        compras = compraDao.findBetweenDates(inicio, fin, 3);
        assertEquals(2, compras.size());
        assertEquals(500.0, compras.get(0).getPrecio());
        assertEquals(1000.0, compras.get(1).getPrecio());

        // Caso donde debería devolver los 3 elementos pero se limita a 2
        inicio = LocalDate.of(2024, 1, 1);
        fin = LocalDate.of(2024, 3, 31);
        compras = compraDao.findBetweenDates(inicio, fin, 2);
        assertEquals(2, compras.size());
        assertEquals(500.0, compras.get(0).getPrecio());
        assertEquals(1000.0, compras.get(1).getPrecio());
    }
    
    @AfterAll
    public void tearDown() {
    	compraDao.delete(compra1);
    	compraDao.delete(compra2);
    	compraDao.delete(compra3);
    }

}
