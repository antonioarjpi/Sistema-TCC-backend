package com.estacio.tcc.builder;

import com.estacio.tcc.model.Tema;

public class TemaBuilder {

    public static Tema criaTema(){
        return Tema.builder()
                .id(null)
                .linhaPesquisa(LinhaPesquisaBuilder.criaLinhaPesquisa())
                .delimitacao("Linguagem de programação Java")
                .build();
    }

    public static Tema temaValido(){
        return Tema.builder()
                .id(1l)
                .linhaPesquisa(LinhaPesquisaBuilder.linhaPesquisaValida())
                .delimitacao("Linguagem de programação Java")
                .build();
    }
}
