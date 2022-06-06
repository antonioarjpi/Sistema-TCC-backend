package com.estacio.tcc.builder;

import com.estacio.tcc.model.Aluno;

import java.util.ArrayList;

public class AlunoDTObuilder {

    public static Aluno criaAluno(){
        return Aluno.builder()
                .nome("nome teste")
                .email("teste@email.com")
                .senha("123456")
                .imagem("teste.png")
                .matricula("123456")
                .build();
    }

}
