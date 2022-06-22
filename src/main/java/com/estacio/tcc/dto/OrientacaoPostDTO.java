package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrientacaoPostDTO {

    private Long id;

    @NotNull
    @FutureOrPresent
    private LocalDate dataOrientacao;

    @NotBlank
    @Size(min = 3, max = 255, message = "Matrícula do orientador tem que ter entre 3 a 255 caracteres")
    private String matriculaOrientador;

    @NotBlank
    @Size(min = 3, max = 255, message = "Tipo de TCC tem que ter entre 3 a 255 caracteres")
    private String tipoTCC;

    @NotBlank
    @Size(min = 3, max = 255, message = "Descrição de TCC tem que ter entre 3 a 255 caracteres")
    private String descricaoTCC;

    @NotNull
    private Long equipe;
}
