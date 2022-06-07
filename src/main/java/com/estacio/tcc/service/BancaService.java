package com.estacio.tcc.service;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.repository.BancaRepository;
import com.estacio.tcc.repository.DefesaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BancaService {

    private BancaRepository repository;
    private OrientadorService orientadorService;
    private EquipeService equipeService;
    private MembroService membroService;
    private DefesaRepository defesaRepository;
    private ModelMapper modelMapper;

    public BancaDTO encontrarIdDTO(Long id){
        Banca banca = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não encontrada"));
        return entidadeParaDTO(banca);
    }

    public Banca encontrarId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não encontrada"));
    }

    @Transactional
    public Banca salvar(BancaPostDTO dto){
        Banca banca = dtoParaEntidade(dto);

        Orientador orientador = orientadorService.encontraMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.encontraId(dto.getEquipe());
        banca.setEquipe(equipe);

        return repository.save(banca);
    }

    @Transactional
    public void deletar(Long id){
        Banca banca = encontrarId(id);
        try {
            repository.deleteById(id);
            membroService.deletar(banca.getMembro());
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Erro! Banca Possui Defesas cadastradas");
        }
    }

    public List<BancaDTO> lista(Banca banca){
        Example example = Example.of(banca, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<BancaDTO>) repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO((Banca) x))
                .collect(Collectors.toList());
    }

    @Transactional
    public Banca atualizar(BancaPostDTO dto){
        Banca banca = dtoParaEntidade(dto);
        Banca novaBanca = encontrarId(banca.getId());

        Orientador orientador = orientadorService.encontraMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.encontraId(dto.getEquipe());
        banca.setEquipe(equipe);

        Membro membro = membroService.encontrarId(novaBanca.getMembro().getId());
        banca.setMembro(membro);
        banca.getMembro().setMatricula(dto.getMembroMatricula());

        banca.setDefesa(novaBanca.getDefesa());

        atualizaDados(novaBanca, banca);
        return repository.save(banca);
    }

    @Transactional
    public Banca agendamentoDefesa(Long id, DefesaPostDTO dto){
        Banca banca = encontrarId(id);
        if (banca.getDefesa() == null) {
            Defesa defesa = new Defesa();
            defesa.setDataDefesa(dto.getData());
            banca.setDefesa(defesa);
        }else {
            Optional<Defesa> defesa = defesaRepository.findById(banca.getDefesa().getId());
            defesa.get().setId(defesa.get().getId());
            defesa.get().setDataDefesa(dto.getData());
            banca.setDefesa(defesa.get());
        }
        return repository.save(banca);
    }


    private void atualizaDados(Banca novaBanca, Banca banca){
        novaBanca.setDataBanca(banca.getDataBanca());
        novaBanca.setDescricao(banca.getDescricao());
        novaBanca.setOrdemApresentacao(banca.getOrdemApresentacao());
        novaBanca.setEquipe(banca.getEquipe());
        novaBanca.setOrientador(banca.getOrientador());
        novaBanca.setMembro(banca.getMembro());
    }

    public Banca dtoParaEntidade(BancaPostDTO dto){
        return modelMapper.map(dto, Banca.class);
    }

    public BancaDTO entidadeParaDTO(Banca banca){
        return modelMapper.map(banca, BancaDTO.class);
    }

}
