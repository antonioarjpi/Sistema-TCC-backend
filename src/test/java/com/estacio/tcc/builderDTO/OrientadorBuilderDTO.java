package com.estacio.tcc.builderDTO;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;

public class OrientadorBuilderDTO {

    public static OrientadorPostDTO criaOrientadorPostDTO(){
        return OrientadorPostDTO.builder()
                .nome("nome teste")
                .email("teste@email.com")
                .senha("123456")
                .titulacaoDescricao("Titulação")
                .titulacaoGrau("Grau")
                .titulacaoIes("IES")
                .linhaPesquisaDescricao("Linha pesquisa")
                .linhaPesquisaAreaconhecimentoDescricao("Area conhecimento")
                .build();
    }

    public static OrientadorDTO orientadorValidoDTO(){
        return OrientadorDTO.builder()
                .id(1l)
                .nome("nome teste")
                .email("teste@email.com")
                .imagem("imagem.png")
                .matricula("matricula")
                .titulacaoDescricao("Titulação")
                .titulacaoGrau("Grau")
                .titulacaoIes("IES")
                .linhaPesquisaDescricao("Linha pesquisa")
                .areaConhecimento("Area conhecimento")
                .build();
    }

    public static OrientadorPostDTO orientadorValidoPostDTO(){
        return OrientadorPostDTO.builder()
                .id(1l)
                .nome("nome teste")
                .email("teste@email.com")
                .titulacaoDescricao("Titulação")
                .titulacaoGrau("Grau")
                .titulacaoIes("IES")
                .linhaPesquisaDescricao("Linha pesquisa")
                .linhaPesquisaAreaconhecimentoDescricao("Area conhecimento")
                .build();
    }


}
