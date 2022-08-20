package com.estacio.tcc.controller;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Membro;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.service.BancaService;
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

@Api(tags = "Banca Controller")
@RestController
@RequestMapping("/bancas")
@AllArgsConstructor
public class BancaController {

    private BancaService service;

    @ApiOperation("Cadastra uma banca no banco de dados")
    @PostMapping
    public ResponseEntity<Banca> salvar(@RequestBody @Valid BancaPostDTO banca) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(banca));
    }

    @ApiOperation("Atualiza cadastro da banca")
    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid BancaPostDTO dto) {
        dto.setId(id);
        Banca banca = service.atualizar(dto);
        return ResponseEntity.ok(banca);
    }

    @ApiOperation("Busca uma banca pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<BancaDTO> encontrarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.encontrarIdDTO(id));
    }

    @ApiOperation("Retorna lista paginada com busca de filtro de banca")
    @GetMapping
    public ResponseEntity listar(Pageable pageable,
                                 @RequestParam(required = false) String descricao,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataBanca,
                                 @RequestParam(required = false) String orientadorNome,
                                 @RequestParam(required = false) Long equipeId,
                                 @RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String membroMatricula) {
        Equipe equipe = new Equipe();
        equipe.setId(equipeId);
        equipe.setDataCadastro(null);

        Orientador orientador = new Orientador();
        orientador.setNome(orientadorNome);

        Membro membro = new Membro();
        membro.setMatricula(membroMatricula);

        Banca banca = new Banca(id, descricao, dataBanca, null, equipe, orientador, membro, null);
        Page<Banca> bancas = service.listaPageada(banca, pageable);
        Page<BancaDTO> page = bancas.map(x -> service.entidadeParaDTO(x));
        return ResponseEntity.ok(page);
    }

    @ApiOperation("Agenda uma defesa pelo id da banca")
    @PutMapping("/agendamento/{id}")
    public ResponseEntity<Banca> agendarDataBanca(@PathVariable Long id, @RequestBody DefesaPostDTO dto) {
        Banca banca = service.agendamentoDefesa(id, dto);
        return ResponseEntity.ok(banca);
    }

    @ApiOperation("Exclui o cadastro da banca pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

