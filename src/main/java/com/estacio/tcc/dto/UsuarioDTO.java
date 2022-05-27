package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
}
