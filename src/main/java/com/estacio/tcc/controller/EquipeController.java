package com.estacio.tcc.controller;

import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeService service;

    @PostMapping
    public ResponseEntity<Equipe> save(@RequestBody EquipeDTO dto){
        return ResponseEntity.ok(service.save(dto));
    }

}
