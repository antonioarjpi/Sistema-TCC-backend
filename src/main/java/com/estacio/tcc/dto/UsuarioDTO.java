package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    @NotBlank
    @Size(min = 2, max = 255, message = "Tamanho do nome tem que ser entre 2 a 255 caracteres")
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 16, message = "Tamanho da senha tem que ser entre 6 a 16 caracteres")
    private String senha;
}
