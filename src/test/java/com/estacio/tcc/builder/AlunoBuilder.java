package com.estacio.tcc.builder;

import com.estacio.tcc.model.Aluno;

public class AlunoBuilder {

    public static Aluno criaAluno(){
        return Aluno.builder()
                .nome("nome teste")
                .email("teste@email.com")
                .senha("123456")
                .imagem("teste.png")
                .matricula("123456")
                .build();
    }

    public static Aluno alunoValido(){
        return Aluno.builder()
                .id(1l)
                .nome("nome teste")
                .email("teste@email.com")
                .senha("123456")
                .imagem("teste.png")
                .matricula("123456")
                .build();
    }

}
