package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.service.OrientadorService;
import com.estacio.tcc.service.TitulaçãoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orientadores")
@AllArgsConstructor
public class OrientadorController {

    private OrientadorService service;
    private TitulaçãoService titulaçãoService;

    @PostMapping
    public ResponseEntity<Orientador> save(@RequestBody OrientadorPostDTO orientador){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(orientador));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orientador> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity search(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String matricula,
                                 @RequestParam(required = false) String grau,
                                 @RequestParam(required = false) String ies,
                                 @RequestParam(required = false) String descricaoTitulacao,
                                 @RequestParam(required = false) String conhecimento,
                                 @RequestParam(required = false) String linhaPesquisa,
                                 @RequestParam(required = false) String email){
        Orientador filter = new Orientador();
        Titulacao titulacao = new Titulacao(null, descricaoTitulacao, grau, ies);
        AreaConhecimento areaConhecimento = new AreaConhecimento(null, conhecimento);
        LinhaPesquisa linha = new LinhaPesquisa(null, linhaPesquisa, areaConhecimento);
        filter.setNome(nome);
        filter.setMatricula(matricula);
        filter.setEmail(email);
        filter.setTitulacao(titulacao);
        filter.setLinhaPesquisa(linha);

        List<OrientadorDTO> search = service.list(filter);
        return ResponseEntity.ok(search);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar (@PathVariable Long id, @RequestBody OrientadorPostDTO dto){
        Orientador byId = service.findById(id);
        Titulacao titulacao = titulaçãoService.findById(byId.getTitulacao().getId());
        titulacao.setDescricao(dto.getTitulacaoDescricao());
        titulacao.setGrau(dto.getTitulacaoGrau());
        titulacao.setIes(dto.getTitulacaoIes());
        Orientador orientador = service.modelToDto(dto);
        orientador.setId(byId.getId());
        orientador.setTitulacao(titulacao);
        orientador.setMatricula(byId.getMatricula());
        service.updateDTO(orientador);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Orientador orientador = service.findById(id);
        service.delete(orientador);
        return ResponseEntity.noContent().build();
    }

}
