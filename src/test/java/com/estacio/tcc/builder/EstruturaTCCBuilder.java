package com.estacio.tcc.builder;

import com.estacio.tcc.model.EstruturaTcc;

public class EstruturaTCCBuilder {

    public static EstruturaTcc criaEstrututaTCC(){
        return EstruturaTcc.builder()
                .id(null)
                .descricao("Descricao Estrututa")
                .tipoTcc(TipoTCCBuilder.criaTipoTCC())
                .build();
    }

    public static EstruturaTcc estruturaTccValido(){
        return EstruturaTcc.builder()
                .id(1l)
                .descricao("Descricao Estrututa")
                .tipoTcc(TipoTCCBuilder.tipoTccValido())
                .build();
    }
}
