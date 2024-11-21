package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.persistencia.clases.entidades.Usuario;
import com.example.demo.persistencia.interfaces.UsuarioDAO;

import jakarta.persistence.PersistenceContext;

@PersistenceContext
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @GetMapping(value="getAllUsuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioDAO.findAllOrderedByNameAsc();
        return ResponseEntity.ok(usuarios);
    }
}
