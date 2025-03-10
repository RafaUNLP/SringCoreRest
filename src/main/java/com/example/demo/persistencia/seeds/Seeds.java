package com.example.demo.persistencia.seeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
    private PasswordEncoder encoder;

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
            usuario1.setNombre("Responsable");
            usuario1.setDni("41106252");
            usuario1.setEmail("marcos@gmail.com");
            usuario1.setPassword(encoder.encode("password"));
            usuario1.setImagen("/img");
            usuario1.setRol(rolResponsableDeTurno);
            usuarioDAO.persist(usuario1);
        } else {
            usuarioDAO.update(usuario1);
        }

        if (usuario2 == null) {
            usuario2 = new Usuario();
            usuario2.setApellido("Gomez");
            usuario2.setNombre("Admin");
            usuario2.setDni("41109313");
            usuario2.setEmail("rafael@gmail.com");
            usuario2.setPassword(encoder.encode("password"));
            usuario2.setImagen("/img");
            usuario2.setRol(rolAdmin);
            usuarioDAO.persist(usuario2);
        } else {
            usuarioDAO.update(usuario2);
        }

        if (usuario3 == null) {
            usuario3 = new Usuario();
            usuario3.setApellido("Gutierrez");
            usuario3.setNombre("Cliente");
            usuario3.setDni("41107713");
            usuario3.setEmail("diego@gmail.com");
            usuario3.setPassword(encoder.encode("password"));
            usuario3.setImagen("/img");
            usuario3.setRol(rolCliente);
            usuarioDAO.persist(usuario3);
        } else {
            usuarioDAO.update(usuario3);
        }

        EnumDia[] enumDias = {EnumDia.LUNES, EnumDia.MARTES, EnumDia.MIERCOLES, EnumDia.JUEVES, EnumDia.VIERNES};

        for (EnumDia enumDia : enumDias) {
            Dia dia = null;
            
            try {dia =  diaDAO.findByEnumDia(enumDia);}
            catch (Exception e) {dia = null;}
            
            if (dia == null) {
                dia = new Dia(enumDia);
                diaDAO.persist(dia);
            }
        }

        return this;
    }
}