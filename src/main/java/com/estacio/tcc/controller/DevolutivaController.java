package com.estacio.tcc.controller;

import com.estacio.tcc.dto.DevolutivaDTO;
import com.estacio.tcc.dto.DevolutivaPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Devolutiva;
import com.estacio.tcc.model.LocalCorrecao;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.service.DevolutivaService;
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
@RequestMapping("/acompanhamentos")
@AllArgsConstructor
public class DevolutivaController {

    private DevolutivaService service;

    @GetMapping
    public ResponseEntity<Page<DevolutivaDTO>> listar(Pageable pageable,
                                                      @RequestParam(required = false) String statusOrientacao,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMudanca,
                                                      @RequestParam(required = false) Long orientacaoId,
                                                      @RequestParam(required = false) String devolutivaDescricao,
                                                      @RequestParam(required = false) String devolutivaVersaoDoc,
                                                      @RequestParam(required = false) String devolutivaLocalCorrecaoCorrecaoSugerida,
                                                      @RequestParam(required = false) String devolutivaLocalCorrecaoLocal) {
        Orientacao orientacao = new Orientacao();
        orientacao.setId(orientacaoId);
        LocalCorrecao correcao = new LocalCorrecao(null, devolutivaLocalCorrecaoLocal, devolutivaLocalCorrecaoCorrecaoSugerida);
        Devolutiva devolutiva = new Devolutiva(null, devolutivaDescricao, devolutivaVersaoDoc, null, correcao);
        AcompanhamentoOrientacao acompanhamento = new AcompanhamentoOrientacao(null, statusOrientacao, dataMudanca, orientacao, devolutiva);

        Page<DevolutivaDTO> devolutivaDTOS = service.listaPageada(acompanhamento, pageable);
        return ResponseEntity.ok(devolutivaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolutivaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.encontrarIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<AcompanhamentoOrientacao> save(@RequestBody @Valid DevolutivaPostDTO dto) {
        AcompanhamentoOrientacao acompanhamento = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(acompanhamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        AcompanhamentoOrientacao acompanhamento = service.encontrarId(id);
        service.deletar(acompanhamento);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid DevolutivaPostDTO dto) {
        dto.setId(id);
        AcompanhamentoOrientacao acompanhamento = service.atualizar(dto);
        return ResponseEntity.ok(acompanhamento);
    }

}
