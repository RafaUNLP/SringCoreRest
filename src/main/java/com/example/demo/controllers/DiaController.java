package com.example.demo.controllers;

import com.example.demo.persistencia.clases.entidades.Dia;
import com.example.demo.persistencia.clases.entidades.EnumDia;
import com.example.demo.persistencia.clases.entidades.MenuEstandar;
import com.example.demo.persistencia.clases.entidades.MenuVegetariano;
import com.example.demo.persistencia.interfaces.DiaDAO;
import com.example.demo.persistencia.interfaces.MenuDAO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/dias/")
@Tag(name="Días", description="CRUD de días")
public class DiaController {

    @Autowired
    private DiaDAO diaDAO;
    @Autowired
    private MenuDAO menuDAO;

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
    
    @GetMapping
    @Operation(summary="Recuperar todos los días")
    public ResponseEntity<List<Dia>> getDias() {
        try {
            List<Dia> dias = diaDAO.findAll();
            if(dias == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(dias, HttpStatus.OK);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
        	Dia anterior = diaDAO.findById(id);
            if (anterior == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            
            dia.setId(id);
            
        /// 	Le saco el seteo del anterior para poder hacer un put de null a la hora de eliminar un menu de un dia en particular
            if(dia.getMenuEstandar() == null)
            	dia.setMenuEstandar(null);
            else 
            	dia.setMenuEstandar((MenuEstandar)menuDAO.persist(dia.getMenuEstandar()));
            
            if(dia.getMenuVegetariano() == null)
            	dia.setMenuVegetariano(null);
            else 
            	dia.setMenuVegetariano((MenuVegetariano)menuDAO.persist(dia.getMenuVegetariano()));             
            
        
        
            
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
