package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoPostDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar o nome")
    private String nome;

    @NotBlank(message = "É obrigatório informar o e-mail")
    private String email;

    @NotBlank(message = "É obrigatório informar a senha")
    private String senha;

}
