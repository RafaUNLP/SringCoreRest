package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.persistencia.clases.DAO.SugerenciaDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Sugerencia;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSugerenciaDAOHibernateJPA {

    @Autowired
    private SugerenciaDAOHibernateJPA sugerenciaDAO;

    @BeforeEach
    public void setUp() {
        // Crear y persistir sugerencias de ejemplo solo si no existen
        if (sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 23)).isEmpty()) {
            Sugerencia sugerencia1 = new Sugerencia("Texto de ejemplo 1", LocalDate.of(2024, 10, 23));
            Sugerencia sugerencia2 = new Sugerencia("Texto de ejemplo 2", LocalDate.of(2024, 10, 22));
            Sugerencia sugerencia3 = new Sugerencia("Texto de ejemplo 3", LocalDate.of(2024, 10, 22));
            sugerenciaDAO.persist(sugerencia1);
            sugerenciaDAO.persist(sugerencia2);
            sugerenciaDAO.persist(sugerencia3);
        }
    }

    @Test
    public void testFindByDate() {
        List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 23));
        assertEquals(1, sugerencias.size());
        assertEquals("Texto de ejemplo 1", sugerencias.get(0).getTexto());

        sugerencias = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 22));
        assertEquals(2, sugerencias.size());
    }

    @Test
    public void testFindByDateWithLimit() {
        List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 22), 1);
        assertEquals(1, sugerencias.size());
    }

    @Test
    public void testFindAllOrderedByDateAsc() {
        List<Sugerencia> sugerencias = sugerenciaDAO.findAllOrderedByDateAsc();
        assertNotNull(sugerencias);
        assertEquals(3, sugerencias.size());
        assertEquals("Texto de ejemplo 2", sugerencias.get(0).getTexto()); // Debe estar en orden ascendente
    }

    @Test
    public void testFindAllOrderedByDateDesc() {
        List<Sugerencia> sugerencias = sugerenciaDAO.findAllOrderedByDateDesc();
        assertNotNull(sugerencias);
        assertEquals(3, sugerencias.size());
        assertEquals("Texto de ejemplo 1", sugerencias.get(0).getTexto()); // Debe estar en orden descendente
    }

    @Test
    public void testPersist() {
        Sugerencia nuevaSugerencia = new Sugerencia("Texto de ejemplo 4", LocalDate.of(2024, 10, 24));
        sugerenciaDAO.persist(nuevaSugerencia);

        List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 24));
        assertNotNull(sugerencias);
        assertEquals(1, sugerencias.size());
        assertEquals("Texto de ejemplo 4", sugerencias.get(0).getTexto());
    }

    @Test
    public void testActualizar() {
        Sugerencia sugerencia = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 23)).get(0);
        sugerencia.setTexto("Texto actualizado");
        sugerenciaDAO.update(sugerencia);

        Sugerencia actualizada = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 23)).get(0);
        assertEquals("Texto actualizado", actualizada.getTexto());
    }

    @Test
    public void testBorrar() {
        Sugerencia sugerencia = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 23)).get(0);
        assertNotNull(sugerencia);

        sugerenciaDAO.delete(sugerencia);
        List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(LocalDate.of(2024, 10, 23));
        assertTrue(sugerencias.isEmpty());
    }
}
