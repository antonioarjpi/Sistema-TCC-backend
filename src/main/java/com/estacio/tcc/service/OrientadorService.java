package com.estacio.tcc.service;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.Titulacao;
import com.estacio.tcc.repository.OrientadorRepository;
import com.estacio.tcc.repository.TitulacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrientadorService {

    private OrientadorRepository repository;
    private TitulacaoRepository titulacaoRepository;
    private ModelMapper modelMapper;

    public Orientador findByMatricula(String matricula){
        Orientador orientador = repository.findByMatricula(matricula);
        if (orientador == null) {
            throw new ObjectNotFoundException("Orientador não encontrado");
        }
        return orientador;
    }

    @Transactional(readOnly = true)
    public List<OrientadorDTO> list(Orientador orientador) {
        Example example = Example.of(orientador, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<OrientadorDTO>) repository.findAll(example)
                .stream()
                .map(x -> dtoToModel((Orientador) x))
                .collect(Collectors.toList());
    }

    public Orientador findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Orientador não encontrado."));
    }

    @Transactional
    public void delete(Orientador orientador){
        Objects.requireNonNull(orientador.getId());
        repository.delete(orientador);
        titulacaoRepository.delete(orientador.getTitulacao());
    }

    @Transactional
    public Orientador save(OrientadorPostDTO dto){
        Orientador orientador = modelToDto(dto);
        String matricula = matriculaValidada();
        orientador.setMatricula(matricula);
        orientador = repository.save(orientador);
        return orientador;
    }

    @Transactional
    public Orientador updateDTO(Orientador orientador){
        Objects.requireNonNull(orientador.getId());
        titulacaoRepository.save(orientador.getTitulacao());
        return repository.save(orientador);
    }

    public String geraMatricula(){
        Random random = new Random();
        String matricula = new String();
        for (int i=0; i<4; i++){ //Gera 4 digitos aleatórios do final da matrícula
            String valueRandom = String.valueOf(random.nextInt(9));
            matricula +=  valueRandom;
        }
        return matricula;
    }

    public String matriculaValidada(){
        String matricula = geraMatricula();
        boolean exists = repository.existsByMatricula(matricula);
        while (exists){
            matriculaValidada();
        }
        return matricula;
    }

    public OrientadorDTO dtoToModel(Orientador orientador){
        return modelMapper.map(orientador, OrientadorDTO.class);
    }

    public Orientador modelToDto(OrientadorPostDTO dto){
        return modelMapper.map(dto, Orientador.class);
    }

}