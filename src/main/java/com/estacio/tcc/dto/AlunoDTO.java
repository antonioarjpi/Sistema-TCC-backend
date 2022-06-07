package com.estacio.tcc.dto;

import com.estacio.tcc.model.Equipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoDTO {

    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private String imagem;
    private List<Equipe> equipe;

}
