package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientacaoDTO;
import com.estacio.tcc.dto.OrientacaoPostDTO;
import com.estacio.tcc.model.EstruturaTcc;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.TipoTcc;
import com.estacio.tcc.service.OrientacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(tags = "Orientação Controller")
@RestController
@RequestMapping("/orientacao")
@AllArgsConstructor
public class OrientacaoController {

    private OrientacaoService service;

    @ApiOperation("Retorna lista paginada com busca de filtro de orientação")
    @GetMapping
    public ResponseEntity listar(Pageable pageable,
                                 @RequestParam(required = false) String nomeOrientador,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataOrientacao,
                                 @RequestParam(required = false) String matriculaOrientador,
                                 @RequestParam(required = false) String tipoTCC,
                                 @RequestParam(required = false) String descricaoTCC) {

        TipoTcc tipoTcc = new TipoTcc(null, tipoTCC);
        EstruturaTcc estruturaTcc = new EstruturaTcc(null, descricaoTCC, tipoTcc);
        estruturaTcc.setTipoTcc(tipoTcc);

        Orientador orientador = new Orientador();
        orientador.setNome(nomeOrientador);
        orientador.setMatricula(matriculaOrientador);

        Orientacao orientacao = new Orientacao(null, dataOrientacao, estruturaTcc, orientador, null, null);
        orientacao.getEstruturaTcc().getTipoTcc().setDescricao(tipoTCC);
        Page<OrientacaoDTO> page = service.listaPageada(orientacao, pageable);
        return ResponseEntity.ok(page);
    }

    @ApiOperation("Busca orientação pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<OrientacaoDTO> encontrarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.encontrarIdDTO(id));
    }

    @ApiOperation("Cadastra orientação no banco de dados")
    @PostMapping
    public ResponseEntity<Orientacao> salvar(@RequestBody @Valid OrientacaoPostDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @ApiOperation("Atualiza cadastro da orientação pelo id")
    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody OrientacaoPostDTO dto) {
        dto.setId(id);
        Orientacao orientacao = service.atualizar(dto);
        return ResponseEntity.ok(orientacao);
    }

    @ApiOperation("Exclui uma orientação pelo id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Orientacao orientacao = service.encontrarId(id);
        service.deletar(orientacao);
        return ResponseEntity.noContent().build();
    }
}
