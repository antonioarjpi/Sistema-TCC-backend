package com.estacio.tcc.service;

import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.repository.AlunoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Transactional
    public Aluno save(Aluno aluno){

        //Gera matrícula automática de 8 dígitos
        Random random = new Random();
        LocalDate dateTime = LocalDate.now();

        String matricula = String.valueOf(dateTime.getYear()); //captura o ano

        for (int i=0; i<4; i++){ //Gera 4 digitos aleatórios do final da matrícula
            String valueRandom = String.valueOf(random.nextInt(9));
            matricula +=  valueRandom;
        }

        aluno.setMatricula(matricula);

        return repository.save(aluno);
    }

    @Transactional
    public Aluno findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado"));
    }

    @Transactional
    public Aluno findByMatricula(String matricula){
        return repository.findByMatricula(matricula);
    }

    @Transactional
    public Aluno findByEmail(String email){
        Aluno aluno = repository.findByEmail(email);
        return aluno;
    }

    public Aluno autenticar(String matricula, String senha){
        Aluno aluno = findByMatricula(matricula);
        if (aluno == null){
            throw new ObjectNotFoundException("Matrícula não encontrada.");
        }
        if (!aluno.getSenha().equals(senha)){
            throw new RuleOfBusinessException("Senha incorreta!");
        }
        return aluno;
    }

    @Transactional
    public Aluno put(Aluno aluno){
        Aluno novoAluno = findByMatricula(aluno.getMatricula());
        aluno.setId(novoAluno.getId());
        update(novoAluno, aluno);
        return repository.save(aluno);
    }

    public void update(Aluno novoAluno, Aluno aluno){
        novoAluno.setNome(aluno.getNome());
        novoAluno.setSenha(aluno.getSenha());
        novoAluno.setEmail(aluno.getEmail());
    }

}
