package com.estacio.tcc.controller;

import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Defesa;
import com.estacio.tcc.service.DefesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/defesas")
public class DefesaController {

    @Autowired
    private DefesaService service;

    @PostMapping
    public ResponseEntity<Defesa> save(@RequestBody DefesaPostDTO dto){
        return ResponseEntity.ok(service.save(dto));
    }
}
