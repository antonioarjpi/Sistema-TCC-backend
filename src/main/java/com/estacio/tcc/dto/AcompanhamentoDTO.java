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
public class AcompanhamentoDTO {

    private Long id;
    private String statusOrientacao;
    private String descricaoDaDevolutiva;
    private String versaoDoc;
    private String localDeCorrecao;
    private String correcaoSugerida;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataMudancao;

}
