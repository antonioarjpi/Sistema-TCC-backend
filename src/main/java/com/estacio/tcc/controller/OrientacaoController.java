package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientacaoDTO;
import com.estacio.tcc.dto.OrientacaoPostDTO;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.service.OrientacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orientacao")
@AllArgsConstructor
public class OrientacaoController {

    private OrientacaoService service;

    @GetMapping
    public ResponseEntity<List<OrientacaoDTO>> list(){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.list());
    }

    @PostMapping
    public ResponseEntity<Orientacao> save(@RequestBody OrientacaoPostDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Orientacao orientacao = service.findById(id);
        service.delete(orientacao);
        return ResponseEntity.noContent().build();
    }
}
