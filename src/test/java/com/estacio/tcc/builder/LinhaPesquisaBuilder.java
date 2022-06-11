package com.estacio.tcc.builder;

import com.estacio.tcc.model.LinhaPesquisa;

public class LinhaPesquisaBuilder {

    public static LinhaPesquisa criaLinhaPesquisa(){
        return LinhaPesquisa.builder()
                .id(null)
                .descricao("Java e suas tecnologias")
                .areaConhecimento(AreaConhecimentoBuilder.criaAreaConhecimento())
                .build();
    }

    public static LinhaPesquisa linhaPesquisaValida(){
        return LinhaPesquisa.builder()
                .id(1l)
                .descricao("Java e suas tecnologias")
                .areaConhecimento(AreaConhecimentoBuilder.areaConhecimentoValido())
                .build();
    }
}
