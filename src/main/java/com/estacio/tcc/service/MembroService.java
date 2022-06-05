package com.estacio.tcc.service;

import com.estacio.tcc.model.Membro;
import com.estacio.tcc.repository.MembroRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MembroService {

    private MembroRepository repository;

    @Transactional
    public Membro encontrarId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Membro da banca não encontrada"));
    }

    @Transactional
    public void deletar(Membro membro){
        repository.delete(membro);
    }
}
