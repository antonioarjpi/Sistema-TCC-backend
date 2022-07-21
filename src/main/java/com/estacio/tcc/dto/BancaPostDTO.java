package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BancaPostDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar a descrição da banca")
    @Size(min = 3, max = 255, message = "Descrição tem que ter entre 3 a 255 caracteres")
    private String descricao;

    @NotNull(message = "É obrigatório informar a data da banca")
    private LocalDate dataBanca;

    @NotNull(message = "É obrigatório a ordem de apresentação")
    private Integer ordemApresentacao;

    @NotBlank(message = "Orientador não pode ser nulo")
    @Size(min = 3, max = 255, message = "Campo tem que ter entre 3 a 6 caracteres")
    private String matriculaOrientador;

    @NotNull(message = "Equipe não pode ser nulo")
    private Long equipe;

    private String membroMatricula;

}
