package com.estacio.tcc.builderDTO;

import com.estacio.tcc.dto.AlunoPostDTO;

public class AlunoBuilderDTO {

    public static AlunoPostDTO criaAlunoDTO(){
        return AlunoPostDTO.builder()
                .nome("nome teste")
                .email("teste@email.com")
                .senha("123456")
                .build();
    }

    public static AlunoPostDTO alunoValidoDTO(){
        return AlunoPostDTO.builder()
                .id(1l)
                .nome("nome teste")
                .email("teste@email.com")
                .build();
    }


}
