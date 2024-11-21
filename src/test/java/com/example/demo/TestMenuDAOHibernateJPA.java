package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.persistencia.clases.DAO.MenuDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Menu;
import com.example.demo.persistencia.clases.entidades.MenuVegetariano;
import com.example.demo.persistencia.clases.entidades.MenuEstandar;

@SpringBootTest
@Transactional
@ActiveProfiles("test")  // Activa el perfil 'test' para las pruebas
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestMenuDAOHibernateJPA {

    @Autowired
    private MenuDAOHibernateJPA menuDAO;

    @Test
    void testPersistMenu() {
        // Test para persistir un Menu en la base de datos
        MenuVegetariano menuVegetariano = new MenuVegetariano(10.0,"Ensalada de verduras","Ensalada de lechuga","Plato principal vegetariano","Fruta","Agua");

        menuDAO.persist(menuVegetariano);

        // Verificar que el menú se haya persistido correctamente
        Menu menuPersistido = menuDAO.findById(menuVegetariano.getId());
        assertNotNull(menuPersistido);
        assertEquals("Ensalada de verduras", menuPersistido.getNombre());
    }

    @Test
    void testFindVegetarians() {
        // Test para verificar que se devuelvan los menús vegetarianos
    	Menu menu1 = new MenuVegetariano(10.0, "Ensalada de frutas", "Ensalada de frutas", "Arroz con vegetales", "Fruta", "Jugo");
        Menu menu2 = new MenuVegetariano(12.0, "Tarta de espinacas", "Tarta de espinacas", "Ensalada de tomate", "Yogur", "Agua");

        menuDAO.persist(menu1);
        menuDAO.persist(menu2);

        List<Menu> menusVegetarianos = menuDAO.findVegetarians();

        assertNotNull(menusVegetarianos);
        assertEquals(2, menusVegetarianos.size());
        assertTrue(menusVegetarianos.stream().anyMatch(m -> m.getNombre().equals("Ensalada de frutas")));
        assertTrue(menusVegetarianos.stream().anyMatch(m -> m.getNombre().equals("Tarta de espinacas")));
    }

    @Test
    void testFindStandards() {
        // Test para verificar que se devuelvan los menús estándar
        Menu menu1 = new MenuEstandar(12.5, "Bife de chorizo", "Ensalada mixta", "Bife de chorizo", "Helado", "Vino tinto");
        Menu menu2 = new MenuEstandar(10, "Pollo al horno", "Ensalada mixta", "Pollo al horno", "Helado", "Vino tinto");

        menuDAO.persist(menu1);
        menuDAO.persist(menu2);

        List<Menu> menusEstandar = menuDAO.findStandards();

        assertNotNull(menusEstandar);
        assertEquals(2, menusEstandar.size());
        assertTrue(menusEstandar.stream().anyMatch(m -> m.getNombre().equals("Bife de chorizo")));
        assertTrue(menusEstandar.stream().anyMatch(m -> m.getNombre().equals("Pollo al horno")));
    }

    @Test
    void testDeleteMenu() {
        // Test para eliminar un Menu de la base de datos
        Menu menu = new MenuVegetariano(10.0, "Ensalada de lentejas", "Ensalada de lentejas", "Pan", "Fruta", "Jugo");

        menuDAO.persist(menu);

        // Eliminar el menú
        menuDAO.delete(menu.getId());

        // Verificar que el menú se haya eliminado correctamente
        Menu menuEliminado = menuDAO.findById(menu.getId());
        assertNull(menuEliminado);  // Confirmamos que ya no existe
    }
}
