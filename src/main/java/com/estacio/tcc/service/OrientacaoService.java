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

    public List<OrientacaoDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    public Orientacao findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Orientação não encontrada"));
    }

    @Transactional
    public Orientacao save(OrientacaoPostDTO dto){
        Orientacao orientacao = modelToDto(dto);
        Orientador orientador = service.findByMatricula(dto.getMatriculaOrientador());
        orientacao.setOrientador(orientador);
        return repository.save(orientacao);
    }

    @Transactional
    public void delete(Orientacao orientacao){
        Objects.requireNonNull(orientacao);
        repository.delete(orientacao);
        estruturaTccRepository.delete(orientacao.getEstruturaTcc());
        tipoTccRepository.delete(orientacao.getEstruturaTcc().getTipoTcc());
    }

    public Orientacao modelToDto(OrientacaoPostDTO dto){
        Orientacao orientacao = new Orientacao();
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
        OrientacaoDTO dto = new OrientacaoDTO();
        dto.setId(orientacao.getId());
        dto.setNome(orientacao.getOrientador().getNome());
        dto.setMatriculaOrientador(orientacao.getOrientador().getMatricula());
        dto.setDataOrientacao(orientacao.getDataOrientacao());
        dto.setDescricaoTCC(orientacao.getEstruturaTcc().getDescricao());
        dto.setTipoTCC(orientacao.getEstruturaTcc().getTipoTcc().getDescricao());
        return dto;
    }

}
