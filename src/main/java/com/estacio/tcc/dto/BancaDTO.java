package com.estacio.tcc.dto;

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
    private Date dataBanca;
    private Integer ordemApresentacao;
    private String orientadorNome;
    private String orientadorMatricula;
    private List equipeAlunos;
    private String equipeId;
    private Integer equipeQuantidade;
    private Date equipeDataCadastro;
    private String membroMatricula;
    private Date defesaDataDefesa;

}
