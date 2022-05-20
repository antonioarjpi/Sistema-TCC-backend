package com.estacio.tcc.service;

import com.estacio.tcc.dto.AcompanhamentoOrientacaoPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Devolutiva;
import com.estacio.tcc.model.LocalCorrecao;
import com.estacio.tcc.repository.AcompanhamentoOrientacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AcompanhamentoOrientacaoService {

    private AcompanhamentoOrientacaoRepository repository;

    public List<AcompanhamentoOrientacao> list(){
        return repository.findAll();
    }

    @Transactional
    public AcompanhamentoOrientacao save(AcompanhamentoOrientacaoPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = modelToDto(dto);
        acompanhamento = repository.save(acompanhamento);
        return acompanhamento;
    }

    public AcompanhamentoOrientacao modelToDto(AcompanhamentoOrientacaoPostDTO dto){
        AcompanhamentoOrientacao acompanhamento = new AcompanhamentoOrientacao();
        acompanhamento.setStatusOrientacao(dto.getStatusOrientacao());
        acompanhamento.setDataMudanca(dto.getDataMudancao());
        acompanhamento.setDevolutiva(Devolutiva
                .builder()
                .descricao(dto.getDescricaoDaDevolutiva())
                .versaoDoc(dto.getVersaoDoc())
                        .localCorrecao(LocalCorrecao
                                .builder()
                                .local(dto.getLocalDeCorrecao())
                                .correcaoSugerida(dto.getCorrecaoSugerida())
                                .build())
                .build());
        return acompanhamento;
    }
}
