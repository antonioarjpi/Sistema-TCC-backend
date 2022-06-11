package com.estacio.tcc.builder;

import com.estacio.tcc.model.LocalCorrecao;

public class LocalCorrecaoBuilder {

    public static LocalCorrecao criaLocalDeCorrecao(){
        return LocalCorrecao.builder()
                .id(null)
                .local("Primeiro Parágrafo")
                .correcaoSugerida("Editar tudo")
                .build();
    }

    public static LocalCorrecao localCorrecaoValido(){
        return LocalCorrecao.builder()
                .id(1l)
                .local("Primeiro Parágrafo")
                .correcaoSugerida("Editar tudo")
                .build();
    }
}
