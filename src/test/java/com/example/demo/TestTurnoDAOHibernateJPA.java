package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.persistencia.clases.DAO.TurnoDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Turno;



@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestTurnoDAOHibernateJPA {

    @Autowired
    private TurnoDAOHibernateJPA turnoDao;


    @BeforeEach
    public void setUp() {
        if (turnoDao.findAllOrderedByInitialHour().isEmpty()) {
            Turno turno1 = new Turno("Mañana", LocalTime.of(8, 0), LocalTime.of(12, 0));
            Turno turno2 = new Turno("Tarde", LocalTime.of(13, 0), LocalTime.of(17, 0));
            Turno turno3 = new Turno("Noche", LocalTime.of(18, 0), LocalTime.of(22, 0));
            turnoDao.persist(turno1);
            turnoDao.persist(turno2);
            turnoDao.persist(turno3);
        }
    }

    @Test
    public void testFindAllOrderedByInitialHour() {
        List<Turno> turnos = turnoDao.findAllOrderedByInitialHour();
        assertNotNull(turnos);
        assertEquals(3, turnos.size());

        assertEquals("Mañana", turnos.get(0).getNombre());
        assertEquals("Tarde", turnos.get(1).getNombre());
        assertEquals("Noche", turnos.get(2).getNombre());
    }
}
