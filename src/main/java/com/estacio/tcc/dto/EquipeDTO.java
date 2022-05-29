package com.estacio.tcc.dto;

import com.estacio.tcc.model.Aluno;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EquipeDTO {

    private Long id;
    private String nome;
    private Long quantidade;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataCadastro;
    private String tema;
    private List<String> alunos;
    private String linhaPesquisa;
    private String conhecimento;

}
