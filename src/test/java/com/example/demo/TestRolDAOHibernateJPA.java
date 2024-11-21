package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.persistencia.clases.DAO.RolDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")  // Activa el perfil 'test' para las pruebas
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestRolDAOHibernateJPA {

    @Autowired
    private RolDAOHibernateJPA rolDAO;

    @BeforeEach
    void setUp() {
        // Spring inyecta rolDAO automáticamente, no es necesario crear la instancia manualmente
    }

    @AfterEach
    void tearDown() {
        // Spring maneja automáticamente el rollback al final de cada prueba, por lo que no es necesario limpiar manualmente
    }

    @Test
    void testFindByName() {
        // Test para encontrar un rol por su nombre
        Rol rol = new ClienteRol();
        rol.setNombre("cliente");

        rolDAO.persist(rol);

        // Buscar el rol por nombre
        Rol rolEncontrado = rolDAO.findByName("cliente");

        assertNotNull(rolEncontrado);
        assertEquals("cliente", rolEncontrado.getNombre());
    }

    @Test
    void testFindByNameNoResult() {
        // Test para cuando no se encuentra ningún rol por el nombre dado
        Rol rolEncontrado = rolDAO.findByName("Inexistente");
        assertNull(rolEncontrado);
    }

    @Test
    void testDeleteRol() {
        // Test para eliminar un Rol de la base de datos
        Rol rol = new ResponsableDeTurnoRol();
        rol.setNombre("responsable_de_turno");

        rolDAO.persist(rol);

        // Eliminar el rol
        rolDAO.delete(rol.getId());

        // Verificar que el rol se haya eliminado correctamente
        Rol rolEliminado = rolDAO.findById(rol.getId());
        assertNull(rolEliminado);
    }
    
    @Test
    void testPersistRol() {
        // Test para persistir un Rol en la base de datos
        Rol rol = new AdministradorRol();
        rol.setNombre("administrador");

        rolDAO.persist(rol);

        // Verificar que el rol se haya persistido correctamente
        Rol rolPersistido = rolDAO.findById(rol.getId());
        assertNotNull(rolPersistido);
        assertEquals("administrador", rolPersistido.getNombre());
    }
}
