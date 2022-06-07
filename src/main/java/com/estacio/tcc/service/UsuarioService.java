package com.estacio.tcc.service;

import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import com.estacio.tcc.service.exceptions.AuthenticateErrorException;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private BCryptPasswordEncoder passwordEncoder;
    private UsuarioRepository repository;

    @Transactional
    public Usuario salva(Usuario usuario){
        criptografarSenha(usuario);
        validaEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    private void criptografarSenha(Usuario usuario) {
        String senha = usuario.getSenha();
        String enconder = passwordEncoder.encode(senha);
        usuario.setSenha(enconder);
    }

    @Transactional
    public Usuario encontraId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
    }

    public Usuario autentica(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()){
            throw new AuthenticateErrorException("Usuário não encontrado.");
        }

        boolean senhas = passwordEncoder.matches(senha, usuario.get().getSenha());

        if (!senhas){
            throw new AuthenticateErrorException("Senha incorreta.");
        }

        return usuario.get();
    }

    public void validaEmail(String email) {
        boolean exist = repository.existsByEmail(email);
        if (exist){
            throw new RuleOfBusinessException("E-mail já está cadastrado.");
        }
    }
}
