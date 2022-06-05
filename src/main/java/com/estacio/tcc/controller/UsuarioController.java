package com.estacio.tcc.controller;

import com.estacio.tcc.dto.UsuarioDTO;
import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid UsuarioDTO userDTO){
        Usuario usuario = Usuario.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .build();
        Usuario save = service.salva(usuario);
        return new ResponseEntity(save, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        Usuario authenticate = service.autentica(dto.getEmail(), dto.getSenha());
        return ResponseEntity.ok(authenticate);
    }
}
