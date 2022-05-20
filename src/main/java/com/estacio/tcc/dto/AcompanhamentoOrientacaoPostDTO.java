package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcompanhamentoOrientacaoPostDTO {

    private Long orientacao;
    private Long devolutiva;
    private String statusOrientacao;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataMudancao;
    private String descricaoDaDevolutiva;
    private String versaoDoc;
    private String localDeCorrecao;
    private String correcaoSugerida;
}
