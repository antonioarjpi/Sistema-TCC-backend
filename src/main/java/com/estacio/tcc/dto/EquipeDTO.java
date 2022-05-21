package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EquipeDTO {

    @NotBlank
    private String nome;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataCadastro;
    private String delimitacao;
    private List<String> alunos;
    private String descricaoLinha;
    private String descricaoConhecimento;

}
