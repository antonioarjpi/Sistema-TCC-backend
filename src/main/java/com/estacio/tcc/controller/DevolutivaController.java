package com.estacio.tcc.controller;

import com.estacio.tcc.dto.DevolutivaDTO;
import com.estacio.tcc.dto.DevolutivaPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Devolutiva;
import com.estacio.tcc.model.LocalCorrecao;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.service.DevolutivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/acompanhamentos")
public class DevolutivaController {

    @Autowired
    private DevolutivaService service;

    @GetMapping
    public ResponseEntity<List<DevolutivaDTO>> list(@RequestParam(required = false) String statusOrientacao,
                                                    @RequestParam(required = false) Date dataMudanca,
                                                    @RequestParam(required = false) Long orientacaoId,
                                                    @RequestParam(required = false) String devolutivaDescricao,
                                                    @RequestParam(required = false) String devolutivaVersaoDoc,
                                                    @RequestParam(required = false) String devolutivaLocalCorrecaoCorrecaoSugerida,
                                                    @RequestParam(required = false) String devolutivaLocalCorrecaoLocal){
        Orientacao orientacao = new Orientacao();
        orientacao.setId(orientacaoId);
        LocalCorrecao correcao = new LocalCorrecao(null, devolutivaLocalCorrecaoLocal, devolutivaLocalCorrecaoCorrecaoSugerida);
        Devolutiva devolutiva = new Devolutiva(null, devolutivaDescricao, devolutivaVersaoDoc, null, correcao);
        AcompanhamentoOrientacao acompanhamento = new AcompanhamentoOrientacao(null, statusOrientacao, dataMudanca, null, devolutiva);

        List<DevolutivaDTO> filter = service.list(acompanhamento);
        return ResponseEntity.ok(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolutivaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.search(id));
    }

    @PostMapping
    public ResponseEntity<AcompanhamentoOrientacao> save(@RequestBody @Valid DevolutivaPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(acompanhamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        AcompanhamentoOrientacao acompanhamento = service.findById(id);
        service.delete(acompanhamento);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid DevolutivaPostDTO dto){
        dto.setId(id);
        AcompanhamentoOrientacao acompanhamento = service.update(dto);
        return ResponseEntity.ok(acompanhamento);
    }

}
