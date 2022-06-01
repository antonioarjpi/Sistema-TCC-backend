package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EquipePostDTO {


    private String nome;
    private Integer quantidade;
    private Date dataCadastro;
    private String delimitacao;
    private List<String> matricula;
    private String descricaoLinha;
    private String descricaoConhecimento;

}
