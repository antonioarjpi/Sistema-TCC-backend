package com.estacio.tcc.builder;

import com.estacio.tcc.model.Equipe;

import java.time.LocalDate;
import java.util.Collections;

public class EquipeBuilder {

    public static Equipe criaEquipe(){
        return Equipe.builder()
                .id(null)
                .nome("Equipe One")
                .quantidade(3)
                .alunos(Collections.singleton(AlunoBuilder.alunoValido()))
                .tema(TemaBuilder.criaTema())
                .dataCadastro(LocalDate.now())
                .build();
    }

    public static Equipe equipeValida(){
        return Equipe.builder()
                .id(1l)
                .nome("Equipe One")
                .quantidade(3)
                .alunos(Collections.singleton(AlunoBuilder.alunoValido()))
                .tema(TemaBuilder.temaValido())
                .dataCadastro(LocalDate.now())
                .build();
    }
}
