package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrientacaoPostDTO {

    private String matriculaOrientador;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataOrientacao;
    private String tipoTCC;
    private String descricaoTCC;

}
