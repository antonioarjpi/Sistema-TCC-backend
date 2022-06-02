package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevolutivaPostDTO {

    private Long id;
    private String statusOrientacao;
    private Date dataMudanca;
    private Long orientacaoId;
    private String devolutivaDescricao;
    private String devolutivaVersaoDoc;
    private String devolutivaLocalCorrecaoLocal;
    private String devolutivaLocalCorrecaoCorrecaoSugerida;

}
