package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrientacaoDTO {

    private Long id;
    private LocalDate dataOrientacao;
    //Orientador
    private String nomeOrientador;
    private String matriculaOrientador;
    //Estrutura do tcc
    private String descricaoTCC;
    //Tipo do tcc
    private String tipoTccDescricao;
    private Long equipe;


}
