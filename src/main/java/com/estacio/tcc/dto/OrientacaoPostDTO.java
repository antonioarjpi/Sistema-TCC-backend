package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrientacaoPostDTO {

    private Long id;
    private Date dataOrientacao;
    private String matriculaOrientador;
    private String tipoTCC;
    private String descricaoTCC;
    private Long equipe;
}
