package com.example.demo.persistencia.seeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.persistencia.clases.entidades.AdministradorRol;
import com.example.demo.persistencia.clases.entidades.ClienteRol;
import com.example.demo.persistencia.clases.entidades.Dia;
import com.example.demo.persistencia.clases.entidades.EnumDia;
import com.example.demo.persistencia.clases.entidades.ResponsableDeTurnoRol;
import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Usuario;
import com.example.demo.persistencia.interfaces.DiaDAO;
import com.example.demo.persistencia.interfaces.RolDAO;
import com.example.demo.persistencia.interfaces.UsuarioDAO;

@Configuration
public class Seeds {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private DiaDAO diaDAO;
    @Autowired
    private RolDAO rolDAO;

    @Bean
    public Seeds generateData() {
    	
    	//NOTA: si te da errores por instancias "detached" reemplazá los persist por update y va a funcionar

        Rol rolAdmin = rolDAO.findByName("administrador");
        Rol rolCliente = rolDAO.findByName("cliente");
        Rol rolResponsableDeTurno = rolDAO.findByName("responsable-turno");

        if (rolAdmin == null) {
            rolAdmin = new AdministradorRol();
            rolAdmin.setNombre("administrador");
            rolDAO.persist(rolAdmin);
        } else {
            rolDAO.update(rolAdmin);
        }

        if (rolCliente == null) {
            rolCliente = new ClienteRol();
            rolCliente.setNombre("cliente");
            rolDAO.persist(rolCliente);
        } else {
            rolDAO.update(rolCliente);
        }

        if (rolResponsableDeTurno == null) {
            rolResponsableDeTurno = new ResponsableDeTurnoRol();
            rolResponsableDeTurno.setNombre("responsable-turno");
            rolDAO.persist(rolResponsableDeTurno);
        } else {
            rolDAO.update(rolResponsableDeTurno);
        }

        Usuario usuario1 = usuarioDAO.findByDni("41106252");
        Usuario usuario2 = usuarioDAO.findByDni("41109313");
        Usuario usuario3 = usuarioDAO.findByDni("41107713");

        if (usuario1 == null) {
            usuario1 = new Usuario();
            usuario1.setApellido("Levis");
            usuario1.setNombre("Marcos");
            usuario1.setDni("41106252");
            usuario1.setEmail("marcos@gmail.com");
            usuario1.setPassword("password");
            usuario1.setImagen("/img");
            usuario1.setRol(rolAdmin);
            usuarioDAO.persist(usuario1);
        } else {
            usuarioDAO.update(usuario1);
        }

        if (usuario2 == null) {
            usuario2 = new Usuario();
            usuario2.setApellido("Gomez");
            usuario2.setNombre("Rafael");
            usuario2.setDni("41109313");
            usuario2.setEmail("rafael@gmail.com");
            usuario2.setPassword("password");
            usuario2.setImagen("/img");
            usuario2.setRol(rolAdmin);
            usuarioDAO.persist(usuario2);
        } else {
            usuarioDAO.update(usuario2);
        }

        if (usuario3 == null) {
            usuario3 = new Usuario();
            usuario3.setApellido("Gutierrez");
            usuario3.setNombre("Diego");
            usuario3.setDni("41107713");
            usuario3.setEmail("diego@gmail.com");
            usuario3.setPassword("password");
            usuario3.setImagen("/img");
            usuario3.setRol(rolCliente);
            usuarioDAO.persist(usuario3);
        } else {
            usuarioDAO.update(usuario3);
        }

        Dia[] dias = new Dia[5];
        EnumDia[] enumDias = {EnumDia.LUNES, EnumDia.MARTES, EnumDia.MIERCOLES, EnumDia.JUEVES, EnumDia.VIERNES};

        for (int i = 0; i < enumDias.length; i++) {
            try { dias[i] = diaDAO.findByEnumDia(enumDias[i]);}
            catch (Exception e) {dias[i] = null;}
        }

        Dia lunes = dias[0]; Dia martes = dias[1]; Dia miercoles = dias[2];Dia jueves = dias[3];Dia viernes = dias[4];

        if (lunes == null) {
            lunes = new Dia(EnumDia.LUNES);
            diaDAO.persist(lunes);
        } else {
            diaDAO.update(lunes);
        }

        if (martes == null) {
            martes = new Dia(EnumDia.MARTES);
            diaDAO.persist(martes);
        } else {
            diaDAO.update(martes);
        }

        if (miercoles == null) {
            miercoles = new Dia(EnumDia.MIERCOLES);
            diaDAO.persist(miercoles);
        } else {
            diaDAO.update(miercoles);
        }

        if (jueves == null) {
            jueves = new Dia(EnumDia.JUEVES);
            diaDAO.persist(jueves);
        } else {
            diaDAO.update(jueves);
        }

        if (viernes == null) {
            viernes = new Dia(EnumDia.VIERNES);
            diaDAO.persist(viernes);
        } else {
            diaDAO.update(viernes);
        }

        return this;
    }
}
