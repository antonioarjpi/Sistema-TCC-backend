package com.estacio.tcc.builder;

import com.estacio.tcc.model.Orientacao;

import java.time.LocalDate;

public class OrientacaoBuilder {

    public static Orientacao criaOrientacao(){
        return Orientacao.builder()
                .id(null)
                .dataOrientacao(LocalDate.of(2022, 06, 11))
                .orientador(OrientadorBuilder.orientadorValido())
                .build();
    }

    public static Orientacao orientacaoValida(){
        return Orientacao.builder()
                .id(1l)
                .dataOrientacao(LocalDate.of(2022, 06, 11))
                .orientador(OrientadorBuilder.orientadorValido())
                .equipe(EquipeBuilder.equipeValidaSemOrientacao().getId())
                .estruturaTcc(EstruturaTCCBuilder.estruturaTccValido())
                .build();
    }

    public static Orientacao orientacaoValida2(){
        return Orientacao.builder()
                .id(1l)
                .dataOrientacao(LocalDate.of(2022, 06, 21))
                .orientador(OrientadorBuilder.orientadorValido())
                .equipe(EquipeBuilder.equipeValida().getId())
                .estruturaTcc(EstruturaTCCBuilder.estruturaTccValido())
                .acompanhamentoOrientacao(AcompanhamentoBuilder.acompanhamentoOrientacaoValido())
                .build();
    }

}
