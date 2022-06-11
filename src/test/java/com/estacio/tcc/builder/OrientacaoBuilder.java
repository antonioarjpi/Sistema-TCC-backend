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
                .build();
    }
}
