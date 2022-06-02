package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Banca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255, message = "Tamanho tem que ser entre 5 a 255 caracteres")
    private String descricao;

    @NotBlank
    private Date dataBanca;

    @NotBlank
    private Integer ordemApresentacao;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "orientador_id")
    private Orientador orientador;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "membro_id")
    private Membro membro;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "defesa_id")
    public Defesa defesa;

}
