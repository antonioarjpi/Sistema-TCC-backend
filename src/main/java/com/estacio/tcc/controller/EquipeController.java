package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AcompanhamentoDTO;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.AreaConhecimento;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.LinhaPesquisa;
import com.estacio.tcc.model.Tema;
import com.estacio.tcc.service.EquipeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/equipes")
@AllArgsConstructor
public class EquipeController {

    private EquipeService service;

    @PostMapping
    public ResponseEntity<Equipe> save(@RequestBody @Valid EquipePostDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity listar(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro,
                                 @RequestParam(required = false) String descricaoConhecimento,
                                 @RequestParam(required = false) String descricaoLinha,
                                 @RequestParam(required = false) String tema,
                                 @RequestParam(required = false) String nome,
                                 Pageable pageable) {
        Equipe equipe = new Equipe();
        AreaConhecimento area = new AreaConhecimento(null, descricaoConhecimento);
        LinhaPesquisa linha = new LinhaPesquisa(null, descricaoLinha, area);
        Tema tema1 = new Tema(null, tema, linha);
        equipe.setNome(nome);
        equipe.setDataCadastro(dataCadastro);
        equipe.setTema(tema1);

        Page<Equipe> equipes = service.listaPageada(equipe, pageable);
        Page<EquipeDTO> map = equipes.map(x -> service.entidadeParaDTO(x));

        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> encontrarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.encontraIdDTO(id));
    }

    @GetMapping("/devolutivas/{id}")
    public ResponseEntity<AcompanhamentoDTO> listarTudo(@PathVariable Long id) {
        return ResponseEntity.ok(service.mostrarTodaEquipePeloId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody EquipePostDTO dto) {
        dto.setId(id);
        Equipe equipe = service.atualizar(dto);
        return ResponseEntity.ok(equipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Equipe equipe = service.encontraId(id);
        service.deletar(equipe);
        return ResponseEntity.noContent().build();
    }
}