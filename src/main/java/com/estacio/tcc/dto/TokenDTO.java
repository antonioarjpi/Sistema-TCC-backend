package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private String nome;
    private String email;

}
