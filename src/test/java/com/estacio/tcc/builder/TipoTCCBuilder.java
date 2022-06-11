package com.estacio.tcc.builder;

import com.estacio.tcc.model.TipoTcc;

public class TipoTCCBuilder {

    public static TipoTcc criaTipoTCC(){
        return TipoTcc.builder()
                .id(null)
                .descricao("Descrição do Tipo de TCC")
                .build();
    }

    public static TipoTcc tipoTccValido(){
        return TipoTcc.builder()
                .id(1l)
                .descricao("Descrição do Tipo de TCC")
                .build();
    }
}
