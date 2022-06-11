package com.estacio.tcc.builder;

import com.estacio.tcc.model.Membro;

public class MembroBuilder {

    public static Membro criaMembro(){
        return Membro.builder()
                .id(null)
                .matricula("2012902")
                .build();
    }

    public static Membro membroValido(){
        return Membro.builder()
                .id(1l)
                .matricula("2012902")
                .build();
    }

}
