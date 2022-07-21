package com.estacio.tcc.dto;

import com.estacio.tcc.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipePostDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar o nome ")
    @Size(min = 3, max = 255, message = "Tema precisa ter entre 3 a 255 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório no campo alunos.")
    private Set<Aluno> alunos;

    @NotBlank(message = "É obrigatório informar a delimitação ")
    @Size(min = 3, max = 255, message = "Tema precisa ter entre 3 a 255 caracteres")
    private String temaDelimitacao;

    @NotBlank(message = "É obrigatório informar a área de conhecimento ")
    @Size(min = 3, max = 500, message = "Descrição de conhecimento precisa ter 3 a 500 caracteres")
    private String temaLinhaPesquisaAreaConhecimentoDescricao;

    @NotBlank(message = "É obrigatório informar a linha de pesquisa")
    @Size(min = 3, max = 5000, message = "Descrição da linha precisa ser entre 3 a 5000 caracteres")
    private String temaLinhapesquisaDescricao;

}
