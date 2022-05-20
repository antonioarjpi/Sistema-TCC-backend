package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AcompanhamentoOrientacaoPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.service.AcompanhamentoOrientacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acompanhamentos")
public class AcompanhamentoOrientacaoController {

    @Autowired
    private AcompanhamentoOrientacaoService service;

    @GetMapping
    public ResponseEntity<List<AcompanhamentoOrientacao>> list(){
        return ResponseEntity.ok(service.list());
    }

    @PostMapping
    public ResponseEntity<AcompanhamentoOrientacao> save(@RequestBody AcompanhamentoOrientacaoPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = service.save(dto);
        return ResponseEntity.ok().body(acompanhamento);
    }
}
