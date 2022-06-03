package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Orientacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataOrientacao;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "estrutura_tcc_id")
    private EstruturaTcc estruturaTcc;

    @ManyToOne
    @JoinColumn(name = "orientador_id")
    private Orientador orientador;

    private Long equipe;

    @ManyToOne
    @JoinColumn(name = "acompanhamento_id")
    private AcompanhamentoOrientacao acompanhamentoOrientacao;


}
