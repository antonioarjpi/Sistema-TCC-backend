package com.estacio.tcc.service;

import com.estacio.tcc.dto.DevolutivaDTO;
import com.estacio.tcc.dto.DevolutivaPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.repository.AcompanhamentoOrientacaoRepository;
import com.estacio.tcc.repository.OrientacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
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
public class DevolutivaService {

    private AcompanhamentoOrientacaoRepository repository;
    private OrientacaoService orientacaoService;
    private OrientacaoRepository orientacaoRepository;
    private ModelMapper modelMapper;

    public AcompanhamentoOrientacao findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Devolutiva não encontrada"));
    }

    public DevolutivaDTO search(Long id){
        AcompanhamentoOrientacao acompanhamento = findById(id);
        return dtoToModel(acompanhamento);
    }

    public List<DevolutivaDTO> list(AcompanhamentoOrientacao acompanhamento){
        Example<AcompanhamentoOrientacao> example = Example.of(acompanhamento, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example)
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    @Transactional
    public AcompanhamentoOrientacao save(DevolutivaPostDTO dto){
        Orientacao orientacao = orientacaoService.findById(dto.getOrientacaoId());
        if (orientacao.getAcompanhamentoOrientacao() != null){
            throw new RuleOfBusinessException("Orientação já possui acompanhamentos");
        }
        AcompanhamentoOrientacao acompanhamento = modelToDto(dto);
        acompanhamento.setOrientacao(orientacao);
        orientacao.setAcompanhamentoOrientacao(acompanhamento);
        acompanhamento = repository.save(acompanhamento);
        orientacaoRepository.save(orientacao);
        return acompanhamento;
    }

    @Transactional
    public void delete(AcompanhamentoOrientacao acompanhamentoOrientacao){
        Objects.requireNonNull(acompanhamentoOrientacao.getId());
        Orientacao orientacao = orientacaoService.findById(acompanhamentoOrientacao.getOrientacao().getId());
        orientacao.setAcompanhamentoOrientacao(null);
        orientacaoRepository.save(orientacao);
        repository.delete(acompanhamentoOrientacao);
    }

    @Transactional
    public AcompanhamentoOrientacao update(DevolutivaPostDTO dto){
        AcompanhamentoOrientacao novoAcompanhamento = modelToDto(dto);
        AcompanhamentoOrientacao acompanhamento = findById(novoAcompanhamento.getId());

        Orientacao orientacao = orientacaoService.findById(dto.getOrientacaoId());
        novoAcompanhamento.setOrientacao(orientacao);

        novoAcompanhamento.getDevolutiva().getLocalCorrecao().setId(acompanhamento.getDevolutiva().getLocalCorrecao().getId());

        putDevolutiva(acompanhamento, novoAcompanhamento);
        return repository.save(acompanhamento);
    }

    private void putDevolutiva(AcompanhamentoOrientacao nova, AcompanhamentoOrientacao acompanhamento){
        nova.setStatusOrientacao(acompanhamento.getStatusOrientacao());
        nova.setDataMudanca(acompanhamento.getDataMudanca());
        nova.setDevolutiva(acompanhamento.getDevolutiva());
        nova.getDevolutiva().setLocalCorrecao(acompanhamento.getDevolutiva().getLocalCorrecao());
        nova.setOrientacao(acompanhamento.getOrientacao());
    }

    public DevolutivaDTO dtoToModel(AcompanhamentoOrientacao acompanhamentoOrientacao){
        return modelMapper.map(acompanhamentoOrientacao, DevolutivaDTO.class);
    }

    public AcompanhamentoOrientacao modelToDto(DevolutivaPostDTO dto){
        return modelMapper.map(dto, AcompanhamentoOrientacao.class);
    }
}