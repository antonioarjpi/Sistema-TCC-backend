package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class BancaDTO {

    private String descricao;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataBanca;
    private Integer ordemApresentacao;

    //orientador
    private String matriculaOrientador;

    //Equipe
    private Long equipe;

    //memrbo banca
    private String membroMatricula;



}
