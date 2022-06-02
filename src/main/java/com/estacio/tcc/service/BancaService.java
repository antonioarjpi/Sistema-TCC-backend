package com.estacio.tcc.service;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Defesa;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.BancaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BancaService {

    private BancaRepository repository;
    private OrientadorService orientadorService;
    private EquipeService equipeService;
    private MembroService membroService;
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

        Orientador orientador = orientadorService.findByMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.search(dto.getEquipe());
        banca.setEquipe(equipe);

        return repository.save(banca);
    }

    @Transactional
    public void delete(Long id){
        Banca banca = search(id);
        try {
            repository.deleteById(id);
            membroService.delete(banca.getMembro());
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Erro! Banca Possui Defesas cadastradas");
        }
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

    @Transactional
    public Banca attBanca(BancaPostDTO dto){
        Banca banca = modelToDto(dto);
        Banca novaBanca = search(banca.getId());

        Orientador orientador = orientadorService.findByMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.search(dto.getEquipe());
        banca.setEquipe(equipe);

        putBanca(novaBanca, banca);
        return repository.save(banca);
    }

    @Transactional
    public Banca schedule(Long id, DefesaPostDTO dto){
        Banca banca = search(id);
        if (banca.getDefesa() == null) {
            Defesa defesa = new Defesa();
            defesa.setDataDefesa(dto.getData());
            banca.setDefesa(defesa);
        }else {
            banca.getDefesa().setDataDefesa(dto.getData());
        }
        return repository.save(banca);
    }


    private void putBanca(Banca novaBanca, Banca banca){
        novaBanca.setDataBanca(banca.getDataBanca());
        novaBanca.setDescricao(banca.getDescricao());
        novaBanca.setOrdemApresentacao(banca.getOrdemApresentacao());
        novaBanca.setEquipe(banca.getEquipe());
        novaBanca.setOrientador(banca.getOrientador());
        novaBanca.setMembro(banca.getMembro());

    }

    public Banca modelToDto(BancaPostDTO dto){
        return modelMapper.map(dto, Banca.class);
    }

    public BancaDTO dtoToModel(Banca banca){
        return modelMapper.map(banca, BancaDTO.class);
    }

}
