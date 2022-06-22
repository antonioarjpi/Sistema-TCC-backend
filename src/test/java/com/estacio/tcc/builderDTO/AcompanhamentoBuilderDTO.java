package com.estacio.tcc.builderDTO;

import com.estacio.tcc.builder.AcompanhamentoBuilder;
import com.estacio.tcc.dto.DevolutivaPostDTO;

public class AcompanhamentoBuilderDTO {

    public static DevolutivaPostDTO criaAcompanhamentoDTO(){
        return DevolutivaPostDTO.builder()
                .id(null)
                .dataMudanca(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDataMudanca())
                .orientacaoId(1l)
                .statusOrientacao(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getStatusOrientacao())
                .devolutivaVersaoDoc(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getVersaoDoc())
                .devolutivaDescricao(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getDescricao())
                .devolutivaLocalCorrecaoLocal(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getLocalCorrecao().getLocal())
                .devolutivaLocalCorrecaoCorrecaoSugerida(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getLocalCorrecao().getCorrecaoSugerida())
                .build();
    }

    public static DevolutivaPostDTO atualizaAcompanhamentoDTO(){
        return DevolutivaPostDTO.builder()
                .id(1l)
                .dataMudanca(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDataMudanca())
                .orientacaoId(1l)
                .statusOrientacao(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getStatusOrientacao())
                .devolutivaVersaoDoc(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getVersaoDoc())
                .devolutivaDescricao(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getDescricao())
                .devolutivaLocalCorrecaoLocal(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getLocalCorrecao().getLocal())
                .devolutivaLocalCorrecaoCorrecaoSugerida(AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getDevolutiva().getLocalCorrecao().getCorrecaoSugerida())
                .build();
    }

}
