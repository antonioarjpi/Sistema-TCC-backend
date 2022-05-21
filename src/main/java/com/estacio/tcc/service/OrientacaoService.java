package com.estacio.tcc.service;

import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.repository.OrientacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrientacaoService {

    private OrientacaoRepository repository;

    public Orientacao save(Orientacao orientacao){
        return repository.save(orientacao);
    }

}
