package com.estacio.tcc.builder;

import com.estacio.tcc.model.Defesa;

import java.time.LocalDate;

public class DefesaBuilder {

    public static Defesa criaDefesa(){
        return Defesa.builder()
                .id(null)
                .dataDefesa(LocalDate.of(2022, 06, 11))
                .build();
    }

    public static Defesa defesaValida(){
        return Defesa.builder()
                .id(1l)
                .dataDefesa(LocalDate.of(2022, 06, 11))
                .build();
    }
}
