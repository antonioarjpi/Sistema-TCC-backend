package com.estacio.tcc.service;

import com.estacio.tcc.model.Titulacao;
import com.estacio.tcc.repository.TitulacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TitulaçãoService {

    private TitulacaoRepository repository;

    public Titulacao findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Titulação não encontrada"));
    }

}
