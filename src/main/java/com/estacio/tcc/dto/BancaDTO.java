package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BancaDTO {

    private Long id;
    private String descricao;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataBanca;
    private Integer ordemDeApresentacao;
    private String nomeOrientador;
    private List integrantes;
    private String nomeEquipe;
    private Integer tamanhoEquipe;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataEquipe;
    private String membroBanca;

}
