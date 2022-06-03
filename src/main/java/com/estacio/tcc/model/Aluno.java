package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data @AllArgsConstructor @Builder
@NoArgsConstructor
public class Aluno{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 255, message = "Tamanho do nome tem que ser entre 5 a 255 caracteres")
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 16, message = "Tamanho da senha tem que ser entre 6 a 16 caracteres")
    private String senha;

    private String matricula;

    @JsonIgnore
    @ManyToMany(mappedBy = "alunos")
    private List<Equipe> equipe;


}
