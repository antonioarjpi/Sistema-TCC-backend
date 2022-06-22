package com.estacio.tcc.builderDTO;

import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.dto.OrientacaoPostDTO;

import java.time.LocalDate;

public class OrientacaoBuilderDTO {

    public static OrientacaoPostDTO criaOrientacaoDTO(){
        return OrientacaoPostDTO.builder()
                .id(null)
                .descricaoTCC("Estrutura TCC")
                .dataOrientacao(LocalDate.of(2022, 06, 21))
                .tipoTCC("Artigo")
                .matriculaOrientador(OrientadorBuilder.orientadorValido().getMatricula())
                .equipe(EquipeBuilder.equipeValida().getId())
                .build();
    }

    public static OrientacaoPostDTO atualizaOrientacaoPostDTO(){
        return OrientacaoPostDTO.builder()
                .id(1l)
                .descricaoTCC("Descricao Estrututa")
                .dataOrientacao(LocalDate.of(2022, 06, 11))
                .tipoTCC("Descrição do Tipo de TCC")
                .matriculaOrientador(OrientadorBuilder.orientadorValido().getMatricula())
                .equipe(EquipeBuilder.equipeValida().getId())
                .build();
    }

}
