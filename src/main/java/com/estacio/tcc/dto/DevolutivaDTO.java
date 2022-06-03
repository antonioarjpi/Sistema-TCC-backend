package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevolutivaDTO {

    private Long id;
    private String statusOrientacao;
    private LocalDate dataMudanca;
    private Long orientacaoId;
    private String devolutivaDescricao;
    private String devolutivaVersaoDoc;
    private String devolutivaLocalCorrecaoLocal;
    private String devolutivaLocalCorrecaoCorrecaoSugerida;

}
