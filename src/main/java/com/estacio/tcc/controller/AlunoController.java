package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.dto.AlunoPostDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.service.AlunoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity listar(Pageable pageable,
                                 @RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String matricula,
                                 @RequestParam(required = false) String email) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setMatricula(matricula);
        aluno.setEmail(email);

        Page<Aluno> alunoDTOS = alunoService.listaPageada(aluno, pageable);
        Page<AlunoDTO> alunos = alunoDTOS.map(x -> alunoService.entidadeParaDTO(x));

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity encontrarEmail(@PathVariable String email) {
        try {
            Aluno aluno = alunoService.encontraEmail(email);
            return ResponseEntity.ok(aluno.getMatricula());
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> encontrarMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(alunoService.encontraMatricula(matricula));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> encontrarId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.encontrarIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<Aluno> salvar(@RequestBody @Valid AlunoPostDTO alunoPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.salvar(alunoPostDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Aluno aluno = alunoService.encontraId(id);
        alunoService.deletar(aluno);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/imagem/{id}")
    public ResponseEntity<Void> uploadFotoOrientador(@RequestParam MultipartFile file, @PathVariable Long id) {
        URI uri = alunoService.uploadFotoPerfil(file, id);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody AlunoPostDTO alunoPostDTO) {
        alunoPostDTO.setId(id);
        return ResponseEntity.ok(alunoService.atualiza(alunoPostDTO));
    }
}
