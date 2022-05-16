package com.estacio.tcc.controller;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.service.BancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bancas")
public class BancaController {

    @Autowired
    private BancaService service;

    @PostMapping
    public ResponseEntity<Banca> save(@RequestBody BancaDTO banca){
        return ResponseEntity.ok(service.save(banca));
    }
}

