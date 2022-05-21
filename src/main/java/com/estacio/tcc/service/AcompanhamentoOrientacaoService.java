package com.estacio.tcc.service;

import com.estacio.tcc.dto.AcompanhamentoOrientacaoPostDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.repository.AcompanhamentoOrientacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcompanhamentoOrientacaoService {

    private AcompanhamentoOrientacaoRepository repository;
    private OrientadorService orientadorService;
    private OrientacaoService orientacaoService;

    public List<AcompanhamentoOrientacaoPostDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    @Transactional
    public AcompanhamentoOrientacao save(AcompanhamentoOrientacaoPostDTO dto){
        Orientador orientador = orientadorService.findByMatricula(dto.getMatriculaOrientador());
        if (orientador == null){
            throw new ObjectNotFoundException("Matrícula de orientador não encontrado");
        }

        AcompanhamentoOrientacao acompanhamento = modelToDto(dto);

        TipoTcc tipo = new TipoTcc(null, dto.getTipoTCC());
        EstruturaTcc estruturaTcc = new EstruturaTcc(null, dto.getDescricaoTCC(), tipo);
        Orientacao orientacao = new Orientacao(null, dto.getDataOrientacao(), estruturaTcc, orientador);
        Devolutiva devolutiva = new Devolutiva();
        LocalCorrecao correcao = new LocalCorrecao(null, dto.getLocalDeCorrecao(), dto.getCorrecaoSugerida());
        devolutiva.setDescricao(dto.getDescricaoDaDevolutiva());
        devolutiva.setVersaoDoc(dto.getVersaoDoc());
        devolutiva.setOrientacao(orientacao);
        devolutiva.setLocalCorrecao(correcao);

        acompanhamento.setDevolutiva(devolutiva);
        orientacao.setOrientador(orientador);
        orientacaoService.save(orientacao);

        acompanhamento.setOrientacao(orientacao);
        acompanhamento = repository.save(acompanhamento);

        return acompanhamento;
    }

    public AcompanhamentoOrientacaoPostDTO dtoToModel(AcompanhamentoOrientacao acompanhamento){
        AcompanhamentoOrientacaoPostDTO dto = new AcompanhamentoOrientacaoPostDTO();
        dto.setStatusOrientacao(acompanhamento.getStatusOrientacao());
        dto.setDataMudancao(acompanhamento.getDataMudanca());
        dto.setDescricaoDaDevolutiva(acompanhamento.getDevolutiva().getDescricao());
        dto.setVersaoDoc(acompanhamento.getDevolutiva().getVersaoDoc());
        dto.setLocalDeCorrecao(acompanhamento.getDevolutiva().getLocalCorrecao().getLocal());
        dto.setCorrecaoSugerida(acompanhamento.getDevolutiva().getLocalCorrecao().getCorrecaoSugerida());
        dto.setMatriculaOrientador(acompanhamento.getOrientacao().getOrientador().getNome());
        dto.setDataOrientacao(acompanhamento.getOrientacao().getDataOrientacao());
        dto.setDescricaoTCC(acompanhamento.getOrientacao().getEstruturaTcc().getDescricao());
        dto.setTipoTCC(acompanhamento.getOrientacao().getEstruturaTcc().getTipoTcc().getDescricao());
        return dto;
    }

    public AcompanhamentoOrientacao modelToDto(AcompanhamentoOrientacaoPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = new AcompanhamentoOrientacao();
        acompanhamento.setStatusOrientacao(dto.getStatusOrientacao());
        acompanhamento.setDataMudanca(dto.getDataMudancao());
        return acompanhamento;
    }
}
