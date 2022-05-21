package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrientadorDTO {

    private String nome;
    private String matricula;

    //titulacao
    private String descricaoTitulacao;
    private String grau;
    private String IES;


}
