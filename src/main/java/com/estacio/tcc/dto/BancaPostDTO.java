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

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class BancaPostDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255, message = "Descrição tem que ter entre 3 a 255 caracteres")
    private String descricao;

    @NotNull
    @FutureOrPresent(message = "Data banca menor que dia atual. ")
    private LocalDate dataBanca;

    @NotNull
    private Integer ordemApresentacao;

    @NotBlank
    @Size(min = 3, max = 255, message = "Campo tem que ter entre 3 a 6 caracteres")
    private String matriculaOrientador;

    @NotNull
    private Long equipe;

    private String membroMatricula;

}
