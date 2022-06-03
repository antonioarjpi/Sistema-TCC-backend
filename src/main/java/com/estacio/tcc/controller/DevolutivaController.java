package com.estacio.tcc.controller;

import com.estacio.tcc.dto.DevolutivaDTO;
import com.estacio.tcc.dto.DevolutivaPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.service.DevolutivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/acompanhamentos")
public class DevolutivaController {

    @Autowired
    private DevolutivaService service;

    @GetMapping
    public ResponseEntity<List<DevolutivaDTO>> list(){
        return ResponseEntity.ok(service.list());
    }

    @PostMapping
    public ResponseEntity<AcompanhamentoOrientacao> save(@RequestBody @Valid DevolutivaPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(acompanhamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        AcompanhamentoOrientacao acompanhamento = service.findById(id);
        service.delete(acompanhamento);
        return ResponseEntity.noContent().build();
    }

}
