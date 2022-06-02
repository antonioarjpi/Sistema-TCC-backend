package com.estacio.tcc.service;

import com.estacio.tcc.dto.OrientacaoDTO;
import com.estacio.tcc.dto.OrientacaoPostDTO;
import com.estacio.tcc.model.EstruturaTcc;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.TipoTcc;
import com.estacio.tcc.repository.EstruturaTccRepository;
import com.estacio.tcc.repository.OrientacaoRepository;
import com.estacio.tcc.repository.TipoTccRepository;
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
public class OrientacaoService {

    private OrientacaoRepository repository;
    private EstruturaTccRepository estruturaTccRepository;
    private TipoTccRepository tipoTccRepository;
    private OrientadorService service;
    private ModelMapper modelMapper;

    public List<OrientacaoDTO> list(Orientacao orientacao){
        Example<Orientacao> example = Example.of(orientacao, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example)
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    public Orientacao findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Orientação não encontrada"));
    }

    public OrientacaoDTO search(Long id){
        Orientacao orientacao = findById(id);
        return dtoToModel(orientacao);
    }

    @Transactional
    public Orientacao save(OrientacaoPostDTO dto){
        Orientacao orientacao = modelToDto(dto);
        Orientador orientador = service.findByMatricula(dto.getMatriculaOrientador());
        orientacao.setOrientador(orientador);
        return repository.save(orientacao);
    }

    @Transactional
    public Orientacao update(OrientacaoPostDTO dto){
        Orientacao novaOrientacao = modelToDto(dto);
        Orientacao orientacao = findById(novaOrientacao.getId());

        Orientador orientador = service.findByMatricula(dto.getMatriculaOrientador());
        novaOrientacao.setOrientador(orientador);

        novaOrientacao.getEstruturaTcc().setId(orientacao.getEstruturaTcc().getId());
        novaOrientacao.getEstruturaTcc().getTipoTcc()
                .setId(orientacao.getEstruturaTcc().getTipoTcc().getId());
        putOrientacao(orientacao, orientacao);
        return repository.save(orientacao);
    }

    @Transactional
    public void delete(Orientacao orientacao){
        Objects.requireNonNull(orientacao);
        repository.delete(orientacao);
    }

    public Orientacao modelToDto(OrientacaoPostDTO dto){
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

    public OrientacaoDTO dtoToModel(Orientacao orientacao){
        return modelMapper.map(orientacao, OrientacaoDTO.class);
    }

    private void putOrientacao(Orientacao nova, Orientacao orientacao){
        nova.setDataOrientacao(orientacao.getDataOrientacao());
        nova.setOrientador(orientacao.getOrientador());
        nova.setEstruturaTcc(orientacao.getEstruturaTcc());
    }

}
