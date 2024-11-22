package com.example.demo.controllers;

import com.example.demo.persistencia.clases.entidades.Dia;
import com.example.demo.persistencia.clases.entidades.EnumDia;
import com.example.demo.persistencia.interfaces.DiaDAO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/dias/")
@Tag(name="Días", description="CRUD de días")
public class DiaController {

    @Autowired
    private DiaDAO diaDAO;

    @PostMapping
    @Operation(summary="Crear un nuevo Dia si no existe previamente")
    public ResponseEntity<Dia> createDia(@RequestBody Dia dia) {
        try {
            Dia creado = diaDAO.persist(dia);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    @Operation(summary="Recuperar un día por su Id")
    public ResponseEntity<Dia> getDiaById(@PathVariable long id) {
        try {
            Dia dia = diaDAO.findById(id);
            if(dia == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(dia, HttpStatus.OK);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("enum/{enumDia}")
    @Operation(summary="Recuperar un día por su enumerativo")
    public ResponseEntity<Dia> getDiaByEnum(@PathVariable EnumDia enumDia) {
        try {
            Dia dia = diaDAO.findByEnumDia(enumDia);
            if(dia == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(dia, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (NonUniqueResultException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    @Operation(summary="Actualizar un día")
    public ResponseEntity<Dia> updateDia(@PathVariable long id, @RequestBody Dia dia) {
        try {
            if (!diaDAO.exist(id)) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            Dia actualizado = diaDAO.update(dia);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary="Eliminar un día por su Id")
    public ResponseEntity<Void> deleteDia(@PathVariable long id) {
        try {
            if (diaDAO.delete(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}