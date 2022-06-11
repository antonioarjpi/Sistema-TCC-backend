package com.estacio.tcc.builder;

import com.estacio.tcc.model.Orientador;

public class OrientadorBuilder {

    public static Orientador criaOrientador(){
        return Orientador.builder()
                .id(null)
                .email("email@email.com")
                .imagem("imagem.png")
                .matricula("matricula")
                .senha("123456")
                .titulacao(TitulacaoBuilder.criaTitulacao())
                .linhaPesquisa(LinhaPesquisaBuilder.criaLinhaPesquisa())
                .build();
    }

    public static Orientador orientadorValido(){
        return Orientador.builder()
                .id(1l)
                .email("email@email.com")
                .imagem("imagem.png")
                .matricula("matricula")
                .senha("123456")
                .titulacao(TitulacaoBuilder.titulacaoValida())
                .linhaPesquisa(LinhaPesquisaBuilder.linhaPesquisaValida())
                .build();
    }
}
