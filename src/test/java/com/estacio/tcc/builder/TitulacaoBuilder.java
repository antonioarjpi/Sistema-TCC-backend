package com.estacio.tcc.builder;

import com.estacio.tcc.model.Titulacao;

public class TitulacaoBuilder {

    public static Titulacao criaTitulacao(){
        return Titulacao.builder()
                .id(null)
                .descricao("Professor")
                .ies("Estácio")
                .grau("Bacharelado")
                .build();
    }

    public static Titulacao titulacaoValida(){
        return Titulacao.builder()
                .id(1l)
                .descricao("Professor")
                .ies("Estácio")
                .grau("Bacharelado")
                .build();
    }
}
