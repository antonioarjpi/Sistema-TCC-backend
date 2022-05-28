package com.estacio.tcc.controller;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.Titulacao;
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
    public ResponseEntity<List<OrientadorDTO>> list(){
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar (@PathVariable Long id, @RequestBody OrientadorPostDTO dto){
        Orientador byId = service.findById(id);
        Titulacao byId1 = titulaçãoService.findById(byId.getTitulacao().getId());
        Orientador orientador = service.modelToDto(dto);
        orientador.setId(byId.getId());
        orientador.setTitulacao(byId1);
        orientador.setMatricula(byId.getMatricula());
        service.updateDTO(orientador);
        return ResponseEntity.ok(orientador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Orientador orientador = service.findById(id);
        service.delete(orientador);
        return ResponseEntity.noContent().build();
    }

}
