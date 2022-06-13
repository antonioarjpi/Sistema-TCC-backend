package com.estacio.tcc.builder;

import com.estacio.tcc.model.Usuario;

public class UsuarioBuilder {

    public static Usuario criaUsuario(){
        return Usuario.builder()
                .id(1l)
                .nome("nome")
                .senha("senha1")
                .email("email@email.com")
                .build();
    }
}
