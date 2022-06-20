package com.estacio.tcc.builderDTO;

import com.estacio.tcc.builder.AlunoBuilder;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;

import java.time.LocalDate;
import java.util.Set;

public class EquipeBuilderDTO {

    public static EquipePostDTO criaEquipeDTO(){
        return EquipePostDTO.builder()
                .id(null)
                .nome("nome")
                .temaDelimitacao("Tema delimitação")
                .temaLinhaPesquisaAreaConhecimentoDescricao("Area descricao")
                .temaLinhapesquisaDescricao("Linha pesquisa")
                .alunos(Set.of(AlunoBuilder.alunoValido()))
                .build();
    }

    public static EquipePostDTO atualizaEquipeDTO(){
        return EquipePostDTO.builder()
                .id(1l)
                .nome("nome")
                .temaDelimitacao("Tema delimitação")
                .temaLinhaPesquisaAreaConhecimentoDescricao("Area descricao")
                .temaLinhapesquisaDescricao("Linha pesquisa")
                .alunos(Set.of(AlunoBuilder.alunoValido()))
                .build();
    }

    public static EquipeDTO equipeDTO(){
        return EquipeDTO.builder()
                .id(1l)
                .nome("nome")
                .tema("Tema delimitação")
                .conhecimento("Area descricao")
                .quantidade(1l)
                .dataCadastro(LocalDate.now())
                .orientacaoId(1l)
                .linhaPesquisa("Linha pesquisa")
                .alunos(Set.of(AlunoBuilder.alunoValido()))
                .build();
    }
}
