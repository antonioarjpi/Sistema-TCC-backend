package com.estacio.tcc.controller;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.MembroBanca;
import com.estacio.tcc.service.BancaService;
import com.estacio.tcc.service.MembroBancaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bancas")
@AllArgsConstructor
public class BancaController {

    private BancaService service;
    private MembroBancaService membroBancaService;

    @PostMapping
    public ResponseEntity<Banca> save(@RequestBody BancaPostDTO banca){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(banca));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody BancaPostDTO dto){
        Banca bancaAchada = service.search(id);
        MembroBanca membroBanca = membroBancaService.findById(bancaAchada.getId());
        membroBanca.setMatricula(dto.getMembroMatricula());
        membroBanca.setId(membroBanca.getId());

        Banca banca = service.modelToDto(dto);
        banca.setId(banca.getId());
        banca.setMembroBanca(membroBanca);
        banca.setOrientador(bancaAchada.getOrientador());
        banca.setEquipe(bancaAchada.getEquipe());
        banca = service.update(banca);

        return ResponseEntity.ok(banca);
    }

    @GetMapping
    public ResponseEntity list(@RequestParam(required = false) String descricao,
                               @RequestParam(required = false) Date dataBanca,
                               @RequestParam(required = false) String orientadorNome,
                               @RequestParam(required = false) String equipeNome,
                               @RequestParam(required = false) Integer equipeQuantidade,
                               @RequestParam(required = false) String equipeDataCadastro,
                               @RequestParam(required = false) String membroBancaMatricula){

        Banca banca = new Banca();
        banca.setDataBanca(dataBanca);
        banca.setDescricao(descricao);

//        banca.getOrientador().setNome(orientadorNome);
//        banca.getMembroBanca().setMatricula(membroBancaMatricula);
//        banca.getEquipe().setQuantidade(equipeQuantidade);
//        banca.getEquipe().getDataCadastro();
//        banca.getEquipe().setNome(equipeNome);
        List<BancaDTO> filter = service.list(banca);
        return ResponseEntity.ok(filter);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Banca banca = service.search(id);
        service.delete(banca);
        return ResponseEntity.noContent().build();
    }
}

