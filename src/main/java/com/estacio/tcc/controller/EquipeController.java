package com.estacio.tcc.controller;

import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeService service;

    @PostMapping
    public ResponseEntity<Equipe> save(@RequestBody EquipePostDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Equipe equipe = service.search(id);
        service.delete(equipe);
        return ResponseEntity.noContent().build();
    }
}