package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EquipePostDTO {

    //Equipe
    private String nome;
    private Integer quantidade;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataCadastro;

    //Tema
    private String delimitacao;

    //Aluno
    //private List<Aluno> alunos = new ArrayList<>();
    private List<String> matricula;

    //Linha pesquisa orientador
    private String descricaoLinha;

    //Area de conhecimento
    private String descricaoConhecimento;

}
