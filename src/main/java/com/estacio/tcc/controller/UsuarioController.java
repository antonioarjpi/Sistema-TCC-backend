package com.estacio.tcc.controller;

import com.estacio.tcc.config.security.JwtUtil;
import com.estacio.tcc.config.security.UserSS;
import com.estacio.tcc.dto.AutenticacaoDTO;
import com.estacio.tcc.dto.TokenDTO;
import com.estacio.tcc.dto.UsuarioDTO;
import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import com.estacio.tcc.service.UserService;
import com.estacio.tcc.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "Usuario Controller")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtUtil jwtService;

    @ApiOperation("Cadastra usuário para acessar o sistema")
    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody @Valid UsuarioDTO userDTO) {
        Usuario usuario = Usuario.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .build();
        Usuario save = service.salva(usuario);
        return new ResponseEntity(save, HttpStatus.CREATED);
    }

    @ApiOperation("Login de usuário")
    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody AutenticacaoDTO dto) {
        Usuario usuario = service.autentica(dto.getEmail(), dto.getSenha());
        String token = jwtService.gerarToken(usuario.getEmail());
        TokenDTO tokenDTO = new TokenDTO(token, usuario.getNome(), usuario.getEmail());
        return ResponseEntity.ok(tokenDTO);
    }

    @ApiOperation("Atualiza token quando estiver autenticado")
    @PostMapping("/refresh")
    public ResponseEntity refreshToken(HttpServletResponse response) {
        UserSS userSS = UserService.authenticated();
        String token = jwtService.gerarToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        TokenDTO tokenDTO = new TokenDTO(token, null, null);
        return ResponseEntity.ok(tokenDTO);
    }
}
