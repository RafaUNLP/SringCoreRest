package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.persistencia.clases.DAO.RolDAOHibernateJPA;
import com.example.demo.persistencia.clases.DAO.UsuarioDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.AdministradorRol;
import com.example.demo.persistencia.clases.entidades.ClienteRol;
import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Usuario;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestUsuarioDAOHibernateJPA {

    @Autowired
    private UsuarioDAOHibernateJPA usuarioDAO;
    @Autowired
    private RolDAOHibernateJPA rolDAO;

    @BeforeEach
    void setUp() {
        // Crear roles y usuarios de ejemplo si no existen
        if (rolDAO.findAll().isEmpty()) {
            Rol admin = new AdministradorRol();
            admin.setNombre("administrador");
            rolDAO.persist(admin);

            Rol cliente = new ClienteRol();
            cliente.setNombre("cliente");
            rolDAO.persist(cliente);
        }
    }

    @Test
    void testPersistUsuario() {
        // Crear un usuario con rol administrador
        Rol admin = rolDAO.findByName("administrador");
        Usuario usuario = new Usuario("12345678", "password123", "imagen.jpg", "Juan", "Perez", "juan@example.com");
        usuario.setRol(admin);

        usuarioDAO.persist(usuario);

        // Verificar que el usuario fue persistido correctamente
        Usuario usuarioPersistido = usuarioDAO.findById(usuario.getId());
        assertNotNull(usuarioPersistido);
        assertEquals("12345678", usuarioPersistido.getDni());
        assertEquals("Juan", usuarioPersistido.getNombre());
        assertEquals("Perez", usuarioPersistido.getApellido());
    }

    @Test
    void testFindByDni() {
        // Crear y persistir un usuario
        Rol admin = rolDAO.findByName("administrador");
        Usuario usuario = new Usuario("87654321", "password321", "imagen2.jpg", "Maria", "Lopez", "maria@example.com");
        usuario.setRol(admin);

        usuarioDAO.persist(usuario);

        // Buscar el usuario por DNI
        Usuario usuarioEncontrado = usuarioDAO.findByDni("87654321");
        assertNotNull(usuarioEncontrado);
        assertEquals("Maria", usuarioEncontrado.getNombre());
        assertEquals("Lopez", usuarioEncontrado.getApellido());
    }

    @Test
    void testFindByDniNoResult() {
        // Buscar un DNI inexistente
        Usuario usuarioNoEncontrado = usuarioDAO.findByDni("99999999");
        assertNull(usuarioNoEncontrado);
    }

    @Test
    void testFindByRol() {
        // Crear y persistir usuarios con diferentes roles
        Rol admin = rolDAO.findByName("administrador");
        Rol cliente = rolDAO.findByName("cliente");

        Usuario usuario1 = new Usuario("12345678", "password123", "imagen.jpg", "Juan", "Perez", "juan@example.com");
        usuario1.setRol(admin);
        Usuario usuario2 = new Usuario("87654321", "password321", "imagen2.jpg", "Maria", "Lopez", "maria@example.com");
        usuario2.setRol(cliente);

        usuarioDAO.persist(usuario1);
        usuarioDAO.persist(usuario2);

        // Buscar usuarios por rol 'Administrador'
        List<Usuario> usuariosAdmin = usuarioDAO.findByRol(admin);
        assertEquals(1, usuariosAdmin.size());
        assertEquals("Juan", usuariosAdmin.get(0).getNombre());

        // Buscar usuarios por rol 'Cliente'
        List<Usuario> usuariosCliente = usuarioDAO.findByRol(cliente);
        assertEquals(1, usuariosCliente.size());
        assertEquals("Maria", usuariosCliente.get(0).getNombre());
    }

    @Test
    void testFindAllOrderedByNameAsc() {
        // Crear y persistir usuarios con el mismo rol
        Rol admin = rolDAO.findByName("administrador");

        Usuario usuario1 = new Usuario("12345678", "password123", "imagen.jpg", "Juan", "Perez", "juan@example.com");
        Usuario usuario2 = new Usuario("87654321", "password321", "imagen2.jpg", "Maria", "Lopez", "maria@example.com");
        Usuario usuario3 = new Usuario("11223344", "password789", "imagen3.jpg", "Ana", "Diaz", "ana@example.com");

        usuario1.setRol(admin);
        usuario2.setRol(admin);
        usuario3.setRol(admin);

        usuarioDAO.persist(usuario1);
        usuarioDAO.persist(usuario2);
        usuarioDAO.persist(usuario3);

        // Buscar todos los usuarios ordenados por nombre ascendente
        List<Usuario> usuariosOrdenados = usuarioDAO.findAllOrderedByNameAsc();
        assertEquals(3, usuariosOrdenados.size());
        assertEquals("Ana", usuariosOrdenados.get(0).getNombre());
        assertEquals("Juan", usuariosOrdenados.get(1).getNombre());
        assertEquals("Maria", usuariosOrdenados.get(2).getNombre());
    }

    @Test
    void testDeleteUsuario() {
        // Crear y persistir un usuario
        Rol admin = rolDAO.findByName("administrador");
        Usuario usuario = new Usuario("12345678", "password123", "imagen.jpg", "Juan", "Perez", "juan@example.com");
        usuario.setRol(admin);

        usuarioDAO.persist(usuario);

        // Eliminar el usuario
        usuarioDAO.delete(usuario.getId());

        // Verificar que el usuario fue eliminado
        Usuario usuarioEliminado = usuarioDAO.findById(usuario.getId());
        assertNull(usuarioEliminado);
    }
}
