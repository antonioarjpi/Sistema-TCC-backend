package com.estacio.tcc.service;

import com.estacio.tcc.dto.OrientacaoDTO;
import com.estacio.tcc.dto.OrientacaoPostDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.repository.EquipeRepository;
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
public class OrientacaoService {

    private OrientacaoRepository repository;
    private EquipeRepository equipeRepository;
    private OrientadorService service;
    private ModelMapper modelMapper;

    public List<OrientacaoDTO> lista(Orientacao orientacao){
        Example<Orientacao> example = Example.of(orientacao, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO(x))
                .collect(Collectors.toList());
    }

    public Orientacao findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Orientação não encontrada"));
    }

    public OrientacaoDTO encontraIdDTO(Long id){
        Orientacao orientacao = findById(id);
        return entidadeParaDTO(orientacao);
    }

    @Transactional
    public Orientacao salvar(OrientacaoPostDTO dto){
        Orientacao orientacao = dtoParaEntidade(dto);
        Orientador orientador = service.encontraMatricula(dto.getMatriculaOrientador());
        orientacao.setOrientador(orientador);

        Equipe equipe = equipeRepository.findById(dto.getEquipe())
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não localizada!"));
        if (equipe.getOrientacao() != null){
            throw new RuleOfBusinessException("Equipe já cadastrada em outra orientação");
        }
        equipe.setOrientacao(orientacao);
        orientacao.setEquipe(equipe.getId());
        equipeRepository.save(equipe);

        return repository.save(orientacao);
    }

    @Transactional
    public Orientacao atualizar(OrientacaoPostDTO dto){
        Orientacao novaOrientacao = dtoParaEntidade(dto);
        Orientacao orientacao = findById(novaOrientacao.getId());

        Orientador orientador = service.encontraMatricula(dto.getMatriculaOrientador());
        novaOrientacao.setOrientador(orientador);

        novaOrientacao.getEstruturaTcc().setId(orientacao.getEstruturaTcc().getId());
        novaOrientacao.getEstruturaTcc().getTipoTcc()
                .setId(orientacao.getEstruturaTcc().getTipoTcc().getId());
        orientacao.getEstruturaTcc().getTipoTcc().setDescricao(dto.getTipoTCC());
        attOrientacao(orientacao, novaOrientacao);
        return repository.save(orientacao);
    }

    @Transactional
    public void deletar(Orientacao orientacao){
        Objects.requireNonNull(orientacao);
        Equipe equipe = equipeRepository.findById(orientacao.getEquipe())
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não localizada!"));
        equipe.setOrientacao(null);
        equipeRepository.save(equipe);
        repository.delete(orientacao);
    }

    public Orientacao dtoParaEntidade(OrientacaoPostDTO dto){
        Orientacao orientacao = new Orientacao();
        orientacao.setId(dto.getId());
        orientacao.setDataOrientacao(dto.getDataOrientacao());
        orientacao.setEstruturaTcc(EstruturaTcc
                .builder()
                .descricao(dto.getDescricaoTCC())
                        .tipoTcc(TipoTcc
                                .builder()
                                .descricao(dto.getTipoTCC())
                                .build())
                .build());
        return orientacao;
    }

    public OrientacaoDTO entidadeParaDTO(Orientacao orientacao){
        return modelMapper.map(orientacao, OrientacaoDTO.class);
    }

    private void attOrientacao(Orientacao nova, Orientacao orientacao){
        nova.setDataOrientacao(orientacao.getDataOrientacao());
        nova.setOrientador(orientacao.getOrientador());
        nova.setEstruturaTcc(orientacao.getEstruturaTcc());
    }

}
