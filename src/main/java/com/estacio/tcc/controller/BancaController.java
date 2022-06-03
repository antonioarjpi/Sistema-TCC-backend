package com.estacio.tcc.controller;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Membro;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.service.BancaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bancas")
@AllArgsConstructor
public class BancaController {

    private BancaService service;

    @PostMapping
    public ResponseEntity<Banca> save(@RequestBody @Valid BancaPostDTO banca){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(banca));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid BancaPostDTO dto){
        dto.setId(id);
        Banca banca = service.attBanca(dto);
        return ResponseEntity.ok(banca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity list(@RequestParam(required = false) String descricao,
                               @RequestParam(required = false) Date dataBanca,
                               @RequestParam(required = false) String orientadorNome,
                               @RequestParam(required = false) Long equipeId,
                               @RequestParam(required = false) Long id,
                               @RequestParam(required = false) String membroMatricula){
        Equipe equipe = new Equipe();
        equipe.setId(equipeId);
        Orientador orientador = new Orientador();
        orientador.setNome(orientadorNome);
        Membro membro = new Membro(null, membroMatricula);

        Banca banca = new Banca();
        banca.setId(id);
        banca.setDataBanca(dataBanca);
        banca.setDescricao(descricao);
        banca.setEquipe(equipe);
        banca.setOrientador(orientador);
        banca.setMembro(membro);
        List<BancaDTO> filter = service.list(banca);
        return ResponseEntity.ok(filter);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Banca> schedule(@PathVariable Long id, @RequestBody DefesaPostDTO dto){
        Banca schedule = service.schedule(id, dto);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

