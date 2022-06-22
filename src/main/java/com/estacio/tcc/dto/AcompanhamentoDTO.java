package com.estacio.tcc.dto;

import com.estacio.tcc.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcompanhamentoDTO {

    private Long id;
    private String nome;
    private Integer quantidade;
    private LocalDate dataCadastro;
    private String temaDelimitacao;
    private String temaLinhaPesquisaDescricao;
    private String temaLinhaPesquisaAreaConhecimentoDescricao;
    private List<Aluno> alunos;
    private Long orientacaoId;
    private LocalDate orientacaoDataOrientacao;
    private String orientacaoOrientadorNome;
    private String orientacaoOrientadorEmail;
    private String estruturaTCC;
    private String tipoTCC;

    //Devolutivas
    private String statusOrientacao;
    private String dataMudanca;
    private String devolutivaDescricao;
    private String devolutivaVersaoDoc;
    private String devolutivaLocalCorrecao;
    private String devolutivaCorrecaoSugerida;


}
