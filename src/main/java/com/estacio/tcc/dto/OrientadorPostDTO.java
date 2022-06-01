package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrientadorPostDTO {

    private String nome;
    private String matricula;
    private String email;
    private String senha;
    private String titulacaoDescricao;
    private String titulacaoGrau;
    private String titulacaoIes;


}
