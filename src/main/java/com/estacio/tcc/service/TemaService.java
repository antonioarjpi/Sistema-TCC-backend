package com.estacio.tcc.service;

import com.estacio.tcc.model.Tema;
import com.estacio.tcc.repository.TemaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TemaService {

    private TemaRepository repository;

    @Transactional
    public Tema encontraId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Tema não localizado"));
    }
}
