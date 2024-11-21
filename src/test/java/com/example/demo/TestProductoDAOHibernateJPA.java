package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.persistencia.clases.DAO.ProductoDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Producto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")  // Activa el perfil 'test' para las pruebas
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestProductoDAOHibernateJPA {

    @Autowired
    private ProductoDAOHibernateJPA productoDAO;

    @Test
    void testPersistProducto() {
        // Test para persistir un Producto en la base de datos
        Producto producto = new Producto();
        producto.setNombre("Cocucha");
        producto.setPrecio(123);

        productoDAO.persist(producto);

        // Verificar que el producto se haya persistido correctamente
        Producto productoPersistido = productoDAO.findById(producto.getId());
        assertNotNull(productoPersistido);
        assertEquals("Cocucha", productoPersistido.getNombre());
    }

    @Test
    void testFindAllOrderedByName() {
        // Test para verificar que se devuelvan los productos ordenados por nombre
        Producto producto1 = new Producto("Manzana", 100);
        Producto producto2 = new Producto("Banana", 150);
        Producto producto3 = new Producto("Cereza", 200);

        productoDAO.persist(producto1);
        productoDAO.persist(producto2);
        productoDAO.persist(producto3);

        List<Producto> productosOrdenados = productoDAO.findAllOrderedByName();

        assertEquals(3, productosOrdenados.size());
        assertEquals("Banana", productosOrdenados.get(0).getNombre());
        assertEquals("Cereza", productosOrdenados.get(1).getNombre());
        assertEquals("Manzana", productosOrdenados.get(2).getNombre());
    }

    @Test
    void testDeleteProducto() {
        // Test para eliminar un Producto de la base de datos
        Producto producto = new Producto("Pera", 80);

        productoDAO.persist(producto);

        // Eliminar el producto
        productoDAO.delete(producto.getId());

        // Verificar que el producto se haya eliminado correctamente
        Producto productoEliminado = productoDAO.findById(producto.getId());
        assertNull(productoEliminado);
    }
}
