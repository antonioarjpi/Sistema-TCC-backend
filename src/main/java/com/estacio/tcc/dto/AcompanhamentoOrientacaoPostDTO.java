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

    private Long orientacao;
    private String statusOrientacao;
    @NotBlank
    private String descricaoDaDevolutiva;
    @NotBlank
    private String versaoDoc;
    private String localDeCorrecao;
    private String correcaoSugerida;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataMudanca;

}
