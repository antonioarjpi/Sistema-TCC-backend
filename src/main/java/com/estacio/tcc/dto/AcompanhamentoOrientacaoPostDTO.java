package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcompanhamentoOrientacaoPostDTO {

    //Acompanhamento Orientacao
    private String statusOrientacao;

    @NotBlank
    private String descricaoDaDevolutiva;

    @NotBlank
    private String versaoDoc;

    private String localDeCorrecao;

    private String correcaoSugerida;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataMudancao;

    //Orientacao
    private String matriculaOrientador;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataOrientacao;
    private String tipoTCC;
    private String descricaoTCC;


}
