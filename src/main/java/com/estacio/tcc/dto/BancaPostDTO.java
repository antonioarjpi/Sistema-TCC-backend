package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class BancaPostDTO {

    private String descricao;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataBanca;
    private Integer ordemApresentacao;

    //orientador
    private String matriculaOrientador;

    //Equipe
    private Long equipe;

    //memrbo banca
    private String membroMatricula;



}
