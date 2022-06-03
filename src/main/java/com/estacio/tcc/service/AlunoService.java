package com.estacio.tcc.service;

import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.repository.AlunoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class AlunoService {

    private AlunoRepository repository;

    @Transactional
    public Aluno save(Aluno aluno){
        validateEmail(aluno.getEmail());
        String matricula = matriculaValidada();
        aluno.setMatricula(matricula);
        return repository.save(aluno);
    }

    public String gerarMatricula(){
        Random random = new Random();
        LocalDate dateTime = LocalDate.now();

        String matricula = String.valueOf(dateTime.getYear()); //captura o ano

        for (int i=0; i<4; i++){ //Gera 4 digitos aleatórios do final da matrícula
            String valueRandom = String.valueOf(random.nextInt(9));
            matricula +=  valueRandom;
        }
        return matricula;
    }

    public String matriculaValidada(){
        String matricula = gerarMatricula();
        boolean exists = repository.existsByMatricula(matricula);
        while (exists){
            matriculaValidada();
        }
        return matricula;
    }

    public void validateEmail(String email) {
        boolean exist = repository.existsByEmail(email);
        if (exist){
            throw new RuleOfBusinessException("E-mail já está cadastrado por outro aluno.");
        }
    }

    public List<Aluno> list(){
        return repository.findAll();
    }

    @Transactional
    public Aluno search(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado"));
    }

    @Transactional
    public Aluno findByMatricula(String matricula){
        Aluno aluno = repository.findByMatricula(matricula);
        if (aluno == null){
            throw new ObjectNotFoundException("Matricula inexistente");
        }
        return aluno;
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
    public void delete(Aluno aluno){
        Objects.requireNonNull(aluno.getId());
        repository.delete(aluno);
    }

    @Transactional
    public Aluno put(Aluno aluno){
        Aluno novoAluno = search(aluno.getId());
        if (!aluno.getEmail().equals(novoAluno.getEmail())){
            validateEmail(aluno.getEmail());
        }
        update(novoAluno, aluno);
        aluno.setMatricula(novoAluno.getMatricula());
        System.out.println("Aluno: "+aluno.getEmail());
        System.out.println("Novo: "+novoAluno.getEmail());
        return repository.save(aluno);
    }

    public void update(Aluno novoAluno, Aluno aluno){
        novoAluno.setNome(aluno.getNome());
        novoAluno.setSenha(aluno.getSenha());
        novoAluno.setEmail(aluno.getEmail());
    }

    @Transactional(readOnly = true)
    public List<Aluno> search(Aluno aluno) {
        Example example = Example.of(aluno, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

}
