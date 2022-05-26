package com.estacio.tcc.controller;

import com.estacio.tcc.dto.DefesaDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Defesa;
import com.estacio.tcc.service.DefesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defesas")
public class DefesaController {

    @Autowired
    private DefesaService service;

    @PostMapping
    public ResponseEntity<Defesa> save(@RequestBody DefesaPostDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Defesa defesa = service.findById(id);
        service.delete(defesa);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DefesaDTO>> list(){
        return ResponseEntity.ok(service.list());
    }
}
