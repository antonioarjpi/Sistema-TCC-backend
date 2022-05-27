package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.service.AlunoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> findAll(){
        return ResponseEntity.ok(alunoService.list());
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody AlunoDTO aluno){
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

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findMatricula(@PathVariable Long id){
        return ResponseEntity.ok(alunoService.search(id));
    }

    @PostMapping
    public ResponseEntity<Aluno> save(@RequestBody Aluno aluno){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.save(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Aluno aluno = alunoService.search(id);
        alunoService.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Aluno aluno){
        aluno.setId(id);
        return ResponseEntity.ok(alunoService.put(aluno));
    }

}
