package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.service.OrientadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orientadores")
public class OrientadorController {

    @Autowired
    private OrientadorService service;

    @PostMapping
    public ResponseEntity<Orientador> save(@RequestBody OrientadorPostDTO orientador){
        return ResponseEntity.ok(service.save(orientador));
    }

    @GetMapping
    public ResponseEntity<List<Orientador>> list(){
        return ResponseEntity.ok(service.list());
    }

}