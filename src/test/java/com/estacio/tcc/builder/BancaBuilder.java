package com.estacio.tcc.builder;

import com.estacio.tcc.model.Banca;

import java.time.LocalDate;

public class BancaBuilder {

    public static Banca criaBanca(){
        return Banca.builder()
                .id(null)
                .ordemApresentacao(1)
                .membro(MembroBuilder.criaMembro())
                .dataBanca(LocalDate.of(2022, 06, 11))
                .build();
    }

    public static Banca bancaValida(){
        return Banca.builder()
                .id(1l)
                .descricao("descricao")
                .orientador(OrientadorBuilder.orientadorValido())
                .equipe(EquipeBuilder.equipeValida())
                .membro(MembroBuilder.membroValido())
                .ordemApresentacao(1)
                .defesa(DefesaBuilder.defesaValida())
                .dataBanca(LocalDate.of(2022, 06, 11))
                .build();
    }

    public static Banca bancaValidaSemDefesa(){
        return Banca.builder()
                .id(1l)
                .descricao("descricao")
                .orientador(OrientadorBuilder.orientadorValido())
                .equipe(EquipeBuilder.equipeValida())
                .membro(MembroBuilder.membroValido())
                .ordemApresentacao(1)
                .defesa(null)
                .dataBanca(LocalDate.of(2022, 06, 11))
                .build();
    }
}
