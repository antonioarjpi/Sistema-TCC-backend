package com.estacio.tcc.controller;

import com.estacio.tcc.dto.UsuarioDTO;
import com.estacio.tcc.dto.TokenDTO;
import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import com.estacio.tcc.config.security.JwtUtil;
import com.estacio.tcc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtUtil jwtService;

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
        Usuario usuario = service.autentica(dto.getEmail(), dto.getSenha());
        String token = jwtService.gerarToken(usuario);
        TokenDTO tokenDTO = new TokenDTO(token);
        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping("/me")
    public ResponseEntity findBYid(){
        List<Usuario> all = repository.findAll();
        return ResponseEntity.ok(all);
    }
}
