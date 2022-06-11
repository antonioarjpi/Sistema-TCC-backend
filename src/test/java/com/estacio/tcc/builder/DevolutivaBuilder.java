package com.estacio.tcc.builder;

import com.estacio.tcc.model.Devolutiva;

public class DevolutivaBuilder {

    public static Devolutiva criaDevolutiva(){
        return Devolutiva.builder()
                .id(null)
                .localCorrecao(LocalCorrecaoBuilder.criaLocalDeCorrecao())
                .orientacao(OrientacaoBuilder.orientacaoValida())
                .versaoDoc(".doc")
                .descricao("Descrição")
                .build();
    }

    public static Devolutiva devolutivaValida(){
        return Devolutiva.builder()
                .id(1l)
                .localCorrecao(LocalCorrecaoBuilder.localCorrecaoValido())
                .orientacao(OrientacaoBuilder.orientacaoValida())
                .versaoDoc(".doc")
                .descricao("Descrição")
                .build();
    }
}
