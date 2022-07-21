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
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String delimitacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linha_pesquisa_id")
    private LinhaPesquisa linhaPesquisa;
}
