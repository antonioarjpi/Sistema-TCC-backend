package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.service.AlunoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity search(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String matricula,
                                 @RequestParam(required = false) String email){
        Aluno filter = new Aluno();
        filter.setNome(nome);
        filter.setMatricula(matricula);
        filter.setEmail(email);
        List<Aluno> search = alunoService.search(filter);
        return ResponseEntity.ok(search);
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody @Valid AlunoDTO aluno){
        alunoService.autenticar(aluno.getMatricula(), aluno.getSenha());
        return ResponseEntity.ok().body("Usuário logado");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity findByMatricula(@PathVariable String email){
        try{
            Aluno aluno = alunoService.findByEmail(email);
            return ResponseEntity.ok(aluno.getMatricula());
        }catch (ObjectNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> findMatricula(@PathVariable String matricula){
        return ResponseEntity.ok(alunoService.findByMatricula(matricula));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findMatricula(@PathVariable Long id){
        return ResponseEntity.ok(alunoService.search(id));
    }

    @PostMapping
    public ResponseEntity<Aluno> save(@RequestBody @Valid Aluno aluno){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.save(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Aluno aluno = alunoService.search(id);
        alunoService.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/imagem/{id}")
    public ResponseEntity<Void> uploadFotoOrientador(@RequestParam MultipartFile file, @PathVariable Long id){
        URI uri = alunoService.uploadFotoPerfil(file, id);
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Aluno aluno){
        aluno.setId(id);
        return ResponseEntity.ok(alunoService.put(aluno));
    }

}
