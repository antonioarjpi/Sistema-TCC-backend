package com.estacio.tcc.service;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.BancaRepository;
import com.estacio.tcc.repository.MembroBancaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BancaService {

    private BancaRepository repository;
    private OrientadorService orientadorService;
    private EquipeService equipeService;
    private MembroBancaRepository membroBancaRepository;
    private ModelMapper modelMapper;

    public BancaDTO findById(Long id){
        Banca banca = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não encontrada"));
        return dtoToModel(banca);
    }

    public Banca search(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não encontrada"));
    }

    @Transactional
    public Banca save(BancaPostDTO dto){
        Banca banca = modelToDto(dto);

        Orientador orientador = orientadorService.findByMatricula(dto.getOrientadorMatricula());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.search(dto.getEquipeId());
        banca.setEquipe(equipe);

        banca = repository.save(banca);
        return banca;
    }

    @Transactional
    public void delete(Banca banca){
        Objects.requireNonNull(banca.getId());
        repository.delete(banca);
        membroBancaRepository.delete(banca.getMembroBanca());
    }

    public List<BancaDTO> list(Banca banca){
        Example example = Example.of(banca, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<BancaDTO>) repository.findAll(example)
                .stream()
                .map(x -> dtoToModel((Banca) x))
                .collect(Collectors.toList());
    }

    public Banca update(Banca banca){
        Objects.requireNonNull(banca.getId());
        return repository.save(banca);
    }

    public Banca modelToDto(BancaPostDTO dto){
        return modelMapper.map(dto, Banca.class);
    }

    public BancaDTO dtoToModel(Banca banca){
        return modelMapper.map(banca, BancaDTO.class);
    }

}
