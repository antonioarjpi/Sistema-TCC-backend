package com.estacio.tcc.service;

import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.repository.AlunoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
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

}
