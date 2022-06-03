package com.estacio.tcc.dto;

import com.estacio.tcc.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EquipeDTO {

    private Long id;
    private String nome;
    private Long quantidade;
    private LocalDate dataCadastro;
    private String tema;
    private List<Aluno> alunos;
    private String linhaPesquisa;
    private String conhecimento;
    private Long orientacaoId;

}
