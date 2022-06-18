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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bancas")
@AllArgsConstructor
public class BancaController {

    private BancaService service;

    @PostMapping
    public ResponseEntity<Banca> salvar(@RequestBody @Valid BancaPostDTO banca){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(banca));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid BancaPostDTO dto){
        dto.setId(id);
        Banca banca = service.atualizar(dto);
        return ResponseEntity.ok(banca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancaDTO> encontrarId(@PathVariable Long id){
        return ResponseEntity.ok(service.encontrarIdDTO(id));
    }

    @GetMapping
    public ResponseEntity listar(@RequestParam(required = false) String descricao,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataBanca,
                                 @RequestParam(required = false) String orientadorNome,
                                 @RequestParam(required = false) Long equipeId,
                                 @RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String membroMatricula){
        Equipe equipe = new Equipe();
        equipe.setId(equipeId);
        equipe.setDataCadastro(null);

        Orientador orientador = new Orientador();
        orientador.setNome(orientadorNome);

        Membro membro = new Membro();
        membro.setMatricula(membroMatricula);

        Banca banca = new Banca(id, descricao, dataBanca, null, equipe, orientador, membro, null);
        List<BancaDTO> filtro = service.lista(banca);
        return ResponseEntity.ok(filtro);
    }

    @PutMapping("/agendamento/{id}")
    public ResponseEntity<Banca> agendarDataBanca(@PathVariable Long id, @RequestBody DefesaPostDTO dto){
        Banca banca = service.agendamentoDefesa(id, dto);
        return ResponseEntity.ok(banca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

