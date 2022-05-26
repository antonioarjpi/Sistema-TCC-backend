package com.estacio.tcc.service;

import com.estacio.tcc.dto.AcompanhamentoDTO;
import com.estacio.tcc.dto.AcompanhamentoOrientacaoPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Devolutiva;
import com.estacio.tcc.model.LocalCorrecao;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.repository.AcompanhamentoOrientacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcompanhamentoOrientacaoService {

    private AcompanhamentoOrientacaoRepository repository;
    private OrientacaoService orientacaoService;

    public AcompanhamentoOrientacao findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Devolutiva não encontrada"));
    }

    public List<AcompanhamentoDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    @Transactional
    public AcompanhamentoOrientacao save(AcompanhamentoOrientacaoPostDTO dto){
        Orientacao orientacao = orientacaoService.findById(dto.getOrientacao());
        if (orientacao == null){
            throw new ObjectNotFoundException("Orientação não localizada");
        }
        AcompanhamentoOrientacao acompanhamento = modelToDto(dto);
        Devolutiva devolutiva = new Devolutiva();
        LocalCorrecao correcao = new LocalCorrecao(null, dto.getLocalDeCorrecao(), dto.getCorrecaoSugerida());
        devolutiva.setDescricao(dto.getDescricaoDaDevolutiva());
        devolutiva.setVersaoDoc(dto.getVersaoDoc());
        devolutiva.setLocalCorrecao(correcao);
        devolutiva.setOrientacao(orientacao);
        acompanhamento.setOrientacao(orientacao);
        acompanhamento.setDevolutiva(devolutiva);
        acompanhamento = repository.save(acompanhamento);
        return acompanhamento;
    }

    @Transactional
    public void delete(AcompanhamentoOrientacao orientacao){
        Objects.requireNonNull(orientacao.getId());
        repository.delete(orientacao);
    }

    public AcompanhamentoDTO dtoToModel(AcompanhamentoOrientacao acompanhamento){
        AcompanhamentoDTO dto = new AcompanhamentoDTO();
        dto.setId(acompanhamento.getId());
        dto.setStatusOrientacao(acompanhamento.getStatusOrientacao());
        dto.setDataMudancao(acompanhamento.getDataMudanca());
        dto.setDescricaoDaDevolutiva(acompanhamento.getDevolutiva().getDescricao());
        dto.setVersaoDoc(acompanhamento.getDevolutiva().getVersaoDoc());
        dto.setLocalDeCorrecao(acompanhamento.getDevolutiva().getLocalCorrecao().getLocal());
        dto.setCorrecaoSugerida(acompanhamento.getDevolutiva().getLocalCorrecao().getCorrecaoSugerida());
        return dto;
    }

    public AcompanhamentoOrientacao modelToDto(AcompanhamentoOrientacaoPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = new AcompanhamentoOrientacao();
        acompanhamento.setStatusOrientacao(dto.getStatusOrientacao());
        acompanhamento.setDataMudanca(dto.getDataMudanca());
        return acompanhamento;
    }
}
