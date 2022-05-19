package com.estacio.tcc.controller;

import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.service.AlunoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody AlunoDTO aluno){
        alunoService.autenticar(aluno.getMatricula(), aluno.getSenha());
        return ResponseEntity.ok().body("Usuário logado");
    }

    @GetMapping("/{email}")
    public ResponseEntity findByMatricula(@PathVariable String email){
        try{
            Aluno aluno = alunoService.findByEmail(email);
            return ResponseEntity.ok(aluno.getMatricula());
        }catch (ObjectNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Aluno> save(@RequestBody Aluno aluno){
        return ResponseEntity.ok(alunoService.save(aluno));
    }

    @PutMapping("/atualizar/{matricula}")
    public ResponseEntity<Aluno> update(@PathVariable String matricula, @RequestBody Aluno aluno){
        aluno.setMatricula(matricula);
        aluno = alunoService.put(aluno);
        return ResponseEntity.ok().body(aluno);
    }

}
