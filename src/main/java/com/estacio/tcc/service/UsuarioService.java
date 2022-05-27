package com.estacio.tcc.service;

import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import com.estacio.tcc.service.exceptions.AuthenticateErrorException;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;

    @Transactional
    public Usuario save(Usuario usuario){
        validateEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Transactional
    public Usuario findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
    }

    public Usuario authenticate(String email, String senha) {
        Optional<Usuario> user = repository.findByEmail(email);
        if (!user.isPresent()){
            throw new AuthenticateErrorException("Usuário não encontrado.");
        }

        if (!user.get().getSenha().equals(senha)){
            throw new AuthenticateErrorException("Senha incorreta.");
        }
        return user.get();
    }

    public void validateEmail(String email) {
        boolean exist = repository.existsByEmail(email);
        if (exist){
            throw new RuleOfBusinessException("E-mail já está cadastrado.");
        }
    }
}
