package com.estacio.tcc.controller;

import com.estacio.tcc.config.security.JwtUtil;
import com.estacio.tcc.config.security.UserSS;
import com.estacio.tcc.dto.TokenDTO;
import com.estacio.tcc.dto.UsuarioDTO;
import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import com.estacio.tcc.service.UserService;
import com.estacio.tcc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtUtil jwtService;

    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody @Valid UsuarioDTO userDTO){
        Usuario usuario = Usuario.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .build();
        Usuario save = service.salva(usuario);
        return new ResponseEntity(save, HttpStatus.CREATED);
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        Usuario usuario = service.autentica(dto.getEmail(), dto.getSenha());
        String token = jwtService.gerarToken(usuario.getEmail());
        TokenDTO tokenDTO = new TokenDTO(token, usuario.getNome(), usuario.getEmail());
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(HttpServletResponse response){
        UserSS userSS = UserService.authenticated();
        String token = jwtService.gerarToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.ok(token);
    }
}
