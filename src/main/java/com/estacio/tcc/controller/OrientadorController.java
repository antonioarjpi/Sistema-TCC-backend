package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.AreaConhecimento;
import com.estacio.tcc.model.LinhaPesquisa;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.Titulacao;
import com.estacio.tcc.service.OrientadorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;

@Api(tags = "Orientador Controller")
@RestController
@RequestMapping("/orientadores")
@AllArgsConstructor
public class OrientadorController {

    private OrientadorService service;

    @ApiOperation("Cadastra um orientador no banco de dados")
    @PostMapping
    public ResponseEntity<Orientador> salvar(@RequestBody @Valid OrientadorPostDTO orientador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(orientador));
    }

    @ApiOperation("Busca um orientador pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<OrientadorDTO> encontrarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.encontraIdDTO(id));
    }

    @ApiOperation("Retorna lista paginada com busca de filtro de orientador")
    @GetMapping
    public ResponseEntity listar(Pageable pageable,
                                 @RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String matricula,
                                 @RequestParam(required = false) String grau,
                                 @RequestParam(required = false) String ies,
                                 @RequestParam(required = false) String descricaoTitulacao,
                                 @RequestParam(required = false) String conhecimento,
                                 @RequestParam(required = false) String linhaPesquisa,
                                 @RequestParam(required = false) String email) {
        Orientador orientador = new Orientador();
        Titulacao titulacao = new Titulacao(null, descricaoTitulacao, grau, ies);
        AreaConhecimento areaConhecimento = new AreaConhecimento(null, conhecimento);
        LinhaPesquisa linha = new LinhaPesquisa(null, linhaPesquisa, areaConhecimento);
        orientador.setNome(nome);
        orientador.setMatricula(matricula);
        orientador.setEmail(email);
        orientador.setTitulacao(titulacao);
        orientador.setLinhaPesquisa(linha);

        Page<Orientador> orientadores = service.listaPageada(orientador, pageable);
        Page<OrientadorDTO> page = orientadores.map(x -> service.entidadeParaDTO(x));
        return ResponseEntity.ok(page);
    }

    @ApiOperation("Atualiza cadastro de orientador pelo id")
    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody OrientadorPostDTO dto) {
        dto.setId(id);
        Orientador orientador = service.atualiza(dto);
        return ResponseEntity.ok(orientador);
    }

    @ApiOperation("Cadastra imagem de perfil no orientador")
    @PostMapping("/imagem/{id}")
    public ResponseEntity<Void> uploadFotoOrientador(@RequestParam MultipartFile file, @PathVariable Long id) {
        URI uri = service.uploadFotoPerfil(file, id);
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation("Exclui orientador pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Orientador orientador = service.encontraId(id);
        service.delete(orientador);
        return ResponseEntity.noContent().build();
    }

}
