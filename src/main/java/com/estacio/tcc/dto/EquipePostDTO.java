package com.estacio.tcc.dto;

import com.estacio.tcc.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EquipePostDTO {

    @NotBlank
    private String nome;

    @NotNull
    private LocalDate dataCadastro;

    @NotBlank
    @Size(min = 3, max = 255, message = "Tema precisa ter entre 3 a 255 caracteres")
    private String delimitacao;

    @NotNull
    private List<Aluno> alunos;

    @NotBlank
    @Size(min = 3, max = 5000, message = "Descrição da linha precisa ser entre 3 a 5000 caracteres")
    private String descricaoLinha;

    @NotBlank
    @Size(min = 3, max = 500, message = "Descrição de conhecimento precisa ter 3 a 500 caracteres")
    private String descricaoConhecimento;

}
