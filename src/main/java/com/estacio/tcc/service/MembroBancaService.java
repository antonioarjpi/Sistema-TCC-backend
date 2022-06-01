package com.estacio.tcc.service;

import com.estacio.tcc.model.MembroBanca;
import com.estacio.tcc.repository.MembroBancaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MembroBancaService {

    private MembroBancaRepository repository;

    @Transactional
    public MembroBanca findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Membro da banca não encontrada"));
    }
}
