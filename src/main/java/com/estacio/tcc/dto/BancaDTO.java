package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BancaDTO {

    private Long id;
    private String descricao;
    private LocalDate dataBanca;
    private Integer ordemApresentacao;
    private String orientadorNome;
    private String orientadorMatricula;
    private Long equipeId;
    private Integer equipeQuantidade;
    private LocalDate equipeDataCadastro;
    private String membroMatricula;
    private LocalDate defesaDataDefesa;

}
