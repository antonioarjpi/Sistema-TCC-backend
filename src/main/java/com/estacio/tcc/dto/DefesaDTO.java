package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefesaDTO {

    private Long banca;
    private String descricao;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataBanca;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataDefesa;

}
