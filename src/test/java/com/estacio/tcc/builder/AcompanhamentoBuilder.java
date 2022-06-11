package com.estacio.tcc.builder;

import com.estacio.tcc.model.AcompanhamentoOrientacao;

import java.time.LocalDate;

public class AcompanhamentoBuilder {

    public static AcompanhamentoOrientacao criaAcompanhamento(){
        return AcompanhamentoOrientacao.builder()
                .id(null)
                .dataMudanca(LocalDate.of(2022, 06, 11))
                .devolutiva(DevolutivaBuilder.criaDevolutiva())
                .orientacao(OrientacaoBuilder.orientacaoValida())
                .build();
    }

    public static AcompanhamentoOrientacao acompanhamentoOrientacaoValido(){
        return AcompanhamentoOrientacao.builder()
                .id(1l)
                .dataMudanca(LocalDate.of(2022, 06, 11))
                .devolutiva(DevolutivaBuilder.devolutivaValida())
                .orientacao(OrientacaoBuilder.orientacaoValida())
                .build();
    }
}
