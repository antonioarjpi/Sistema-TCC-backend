package com.estacio.tcc.builderDTO;

import com.estacio.tcc.dto.DefesaPostDTO;

import java.time.LocalDate;

public class DefesaBuilderDTO {

    public static DefesaPostDTO criaDefesa(){
        return DefesaPostDTO.builder()
                .data(LocalDate.now())
                .build();
    }
}
