package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrientadorDTO {

    private Long id;
    private String nome;
    private String matricula;
    private String email;
    private String imagem;
    private String titulacaoDescricao;
    private String titulacaoGrau;
    private String titulacaoIes;
    private String linhaPesquisaDescricao;
    private String areaConhecimento;


}
