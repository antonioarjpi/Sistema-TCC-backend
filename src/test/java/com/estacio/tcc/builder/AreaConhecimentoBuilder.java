package com.estacio.tcc.builder;

import com.estacio.tcc.model.AreaConhecimento;

public class AreaConhecimentoBuilder {

    public static AreaConhecimento criaAreaConhecimento(){
        return AreaConhecimento.builder()
                .id(null)
                .descricao("Java POO")
                .build();
    }

    public static AreaConhecimento areaConhecimentoValido(){
        return AreaConhecimento.builder()
                .id(1l)
                .descricao("Java POO")
                .build();
    }
}
