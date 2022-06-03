package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AcompanhamentoDTO;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.service.EquipeService;
import com.estacio.tcc.service.TemaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/equipes")
@AllArgsConstructor
public class EquipeController {

    private EquipeService service;
    private TemaService temaService;

    @PostMapping
    public ResponseEntity<Equipe> save(@RequestBody @Valid EquipePostDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity search(@RequestParam(required = false) Date dataCadastro,
                                 @RequestParam(required = false) String descricaoConhecimento,
                                 @RequestParam(required = false) String descricaoLinha,
                                 @RequestParam(required = false) String tema,
                                 @RequestParam(required = false) String nome){
        Equipe filter = new Equipe();
        AreaConhecimento area = new AreaConhecimento(null, descricaoConhecimento);
        LinhaPesquisa linha = new LinhaPesquisa(null, descricaoLinha, area );
        Tema tema1 = new Tema(null, tema, linha);
        filter.setNome(nome);
        filter.setDataCadastro(dataCadastro);
        filter.setTema(tema1);
        List<EquipeDTO> search = service.list(filter);
        return ResponseEntity.ok(search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/devolutivas/{id}")
    public ResponseEntity<AcompanhamentoDTO> findByIdFullSearch(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdFullSearch(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody EquipePostDTO dto){
        Equipe search = service.search(id);
        Tema tema = temaService.findById(search.getTema().getId());
        tema.setDelimitacao(dto.getDelimitacao());
        tema.getLinhaPesquisa().setDescricao(dto.getDescricaoLinha());
        tema.getLinhaPesquisa().getAreaConhecimento().setDescricao(dto.getDescricaoConhecimento());

        Equipe equipe = service.modelToDto(dto);
        equipe.setId(search.getId());
        equipe.setAlunos(dto.getAlunos());
        equipe.setTema(tema);
        equipe.setDataCadastro(search.getDataCadastro());
        equipe = service.updateDTO(equipe);
        return ResponseEntity.ok(equipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Equipe equipe = service.search(id);
        service.delete(equipe);
        return ResponseEntity.noContent().build();
    }
}