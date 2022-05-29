package com.estacio.tcc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrientacaoDTO {

    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataOrientacao;
    //Orientador
    private String nomeOrientador;
    private String matriculaOrientador;
    //Estrutura do tcc
    private String descricaoTCC;
    //Tipo do tcc
    private String tccDescricao;


}
