package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class BancaPostDTO {

    private Long id;
    private String descricao;
    private Date dataBanca;
    private Integer ordemApresentacao;
    private String matriculaOrientador;
    private Long equipe;
    private String membroMatricula;

}
