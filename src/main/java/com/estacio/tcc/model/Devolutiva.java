package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Devolutiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String versaoDoc;

    @ManyToOne
    @JoinColumn(name = "orientacao_id")
    private Orientacao orientacao;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "local_correcao_id")
    private LocalCorrecao localCorrecao;

}
