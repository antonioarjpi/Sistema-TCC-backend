package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String descricao;

    private Date dataBanca;

    private Integer ordemApresentacao;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

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
