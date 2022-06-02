package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Equipe{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 60, message = "Tamanho tem que ser entre 3 a 60 caracteres")
    private String nome;

    @NotBlank
    private Integer quantidade;

    @NotBlank
    private Date dataCadastro;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "aluno_equipe",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id"))
    private List<Aluno> alunos;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "tema_id")
    private Tema tema;

    @OneToOne
    @JoinColumn(name = "orientacao_id")
    private Orientacao orientacao;

}
