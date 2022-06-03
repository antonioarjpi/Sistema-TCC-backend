package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientacaoDTO;
import com.estacio.tcc.dto.OrientacaoPostDTO;
import com.estacio.tcc.model.EstruturaTcc;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.TipoTcc;
import com.estacio.tcc.service.OrientacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orientacao")
@AllArgsConstructor
public class OrientacaoController {

    private OrientacaoService service;

    @GetMapping
    public ResponseEntity list(@RequestParam(required = false) String nomeOrientador,
                               @RequestParam(required = false) LocalDate dataOrientacao,
                               @RequestParam(required = false) String matriculaOrientador,
                               @RequestParam(required = false) String tccDescricao,
                               @RequestParam(required = false) String descricaoTCC){
        EstruturaTcc estruturaTcc = new EstruturaTcc();
        estruturaTcc.setDescricao(descricaoTCC);

        TipoTcc tipoTcc = new TipoTcc();
        tipoTcc.setDescricao(tccDescricao);

        Orientador orientador = new Orientador();
        orientador.setNome(nomeOrientador);
        orientador.setMatricula(matriculaOrientador);

        Orientacao orientacao = new Orientacao(null, dataOrientacao, estruturaTcc, orientador, null, null);
        List<OrientacaoDTO> filter = service.list(orientacao);
        return ResponseEntity.ok(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrientacaoDTO> search(@PathVariable Long id){
        return ResponseEntity.ok(service.search(id));
    }

    @PostMapping
    public ResponseEntity<Orientacao> save(@RequestBody @Valid OrientacaoPostDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody OrientacaoPostDTO dto){
        dto.setId(id);
        Orientacao orientacao = service.update(dto);
        return ResponseEntity.ok(orientacao);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Orientacao orientacao = service.findById(id);
        service.delete(orientacao);
        return ResponseEntity.noContent().build();
    }
}
