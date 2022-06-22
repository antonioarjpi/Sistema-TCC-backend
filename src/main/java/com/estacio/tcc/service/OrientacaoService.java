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

    //Retorna lista de orientação DTO
    public List<OrientacaoDTO> lista(Orientacao orientacao){
        Example<Orientacao> example = Example.of(orientacao, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO(x))
                .collect(Collectors.toList());
    }

    //Retorna Orientação se achar o ID ou retorna erro
    public Orientacao encontrarId(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Orientação não encontrada"));
    }

    //Retorna Orientação DTO se encontrar o ID ou retorna erro se não achar
    public OrientacaoDTO encontrarIdDTO(Long id){
        Orientacao orientacao = encontrarId(id);
        return entidadeParaDTO(orientacao);
    }

    //Salva Orientação no banco de dados
    @Transactional
    public Orientacao salvar(OrientacaoPostDTO dto){
        Orientacao orientacao = dtoParaEntidade(dto);

        Equipe equipe = buscarOrientadorEquipe(dto, orientacao);

        //Retorna erro se equipe já possuir orientação
        if (equipe.getOrientacao() != null){
            throw new RuleOfBusinessException("Equipe já cadastrada em outra orientação");
        }
        
        equipe.setOrientacao(orientacao);
        orientacao.setEquipe(equipe.getId());
        equipeRepository.save(equipe);

        return repository.save(orientacao);
    }

    //Atualização da orientação
    @Transactional
    public Orientacao atualizar(OrientacaoPostDTO dto){
        Orientacao novaOrientacao = dtoParaEntidade(dto);
        Orientacao orientacao = encontrarId(novaOrientacao.getId());

        Equipe equipe = buscarOrientadorEquipe(dto, orientacao);

        //Retorna erro quando for alterar equipe e ela já possui orientação
        if (!orientacao.getEquipe().equals(equipe.getId()) && equipe.getOrientacao() != null){
            throw new RuleOfBusinessException("Equipe já cadastrada por outra orientação");
        }

        equipe.setOrientacao(orientacao);
        orientacao.setEquipe(equipe.getId());
        equipeRepository.save(equipe);
        attOrientacao(orientacao, novaOrientacao);
        return repository.save(orientacao);
    }

    //Metódo para buscar equipe e orientador, retornando a equipe.
    private Equipe buscarOrientadorEquipe(OrientacaoPostDTO dto, Orientacao orientacao) {
        Orientador orientador = service.encontraMatricula(dto.getMatriculaOrientador());
        orientacao.setOrientador(orientador);

        Equipe equipe = equipeRepository.findById(dto.getEquipe())
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não localizada!"));
        return equipe;
    }

    private void attOrientacao(Orientacao nova, Orientacao orientacao){
        //Guarda id's
        Long idEstruturaTcc = nova.getEstruturaTcc().getId();
        Long idTipoTcc = nova.getEstruturaTcc().getTipoTcc().getId();

        //Orientações
        nova.setId(nova.getId());
        nova.setDataOrientacao(orientacao.getDataOrientacao());

        //Estruturas de TCC
        nova.setEstruturaTcc(orientacao.getEstruturaTcc());
        nova.getEstruturaTcc().setId(idEstruturaTcc);
        nova.getEstruturaTcc().getTipoTcc().setId(idTipoTcc);
    }

    //Deleta orientação e remove orientação da equipe
    @Transactional
    public void deletar(Orientacao orientacao){
        Objects.requireNonNull(orientacao);
        Equipe equipe = equipeRepository.findById(orientacao.getEquipe())
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não localizada!"));
        equipe.setOrientacao(null);
        equipeRepository.save(equipe);
        repository.delete(orientacao);
    }

    //Converte as entradas dto para entidade
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


    //Converte as entidades para DTO.
    public OrientacaoDTO entidadeParaDTO(Orientacao orientacao){
        return modelMapper.map(orientacao, OrientacaoDTO.class);
    }

    

}