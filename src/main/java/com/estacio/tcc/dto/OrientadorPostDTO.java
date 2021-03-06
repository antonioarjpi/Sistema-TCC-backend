package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrientadorPostDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar o nome ")
    @Size(min = 3, max = 255, message = "Campo tem que ter entre 3 a 255 caracteres")
    private String nome;

    @NotBlank(message = "É obrigatório informar o e-mail ")
    @Email(message = "Deve ser um e-mail válido")
    private String email;

    @NotBlank(message = "É obrigatório informar a senha")
    @Size(min = 6, max = 16, message = "Senha tem que ser entre 6 a 16 caracteres")
    private String senha;

    @NotBlank(message = "É obrigatório informar a descrição da titulação ")
    @Size(min = 3, max = 100, message = "Descrição da titulação tem que ter entre 3 a 100 caracteres")
    private String titulacaoDescricao;

    @NotBlank(message = "É obrigatório informar o grau ")
    @Size(min = 3, max = 100, message = "Grau tem que ter entre 3 a 100 caracteres")
    private String titulacaoGrau;

    @NotBlank(message = "É obrigatório informar a IES ")
    @Size(min = 3, max = 100, message = "IES tem que ter entre 3 a 100 caracteres")
    private String titulacaoIes;

    @NotBlank(message = "É obrigatório informar a linha de pesquisa ")
    @Size(min = 3, max = 255, message = "Linha de pesquisa tem que ter entre 3 a 255 caracteres")
    private String linhaPesquisaDescricao;

    @NotBlank(message = "É obrigatório informar a area de conhecimento ")
    @Size(min = 3, max = 255, message = "Area de conhecimento tem que ter entre 3 a 255 caracteres")
    private String linhaPesquisaAreaconhecimentoDescricao;

}
