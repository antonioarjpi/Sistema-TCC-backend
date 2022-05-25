package com.estacio.tcc.controller;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.service.BancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bancas")
public class BancaController {

    @Autowired
    private BancaService service;

    @PostMapping
    public ResponseEntity<Banca> save(@RequestBody BancaPostDTO banca){
        return ResponseEntity.ok(service.save(banca));
    }

    @GetMapping
    public ResponseEntity<List<BancaDTO>> list(){
        return ResponseEntity.ok(service.list());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Banca banca = service.findById(id);
        service.delete(banca);
        return ResponseEntity.noContent().build();
    }
}

