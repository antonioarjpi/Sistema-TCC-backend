package com.estacio.tcc.service;

import com.estacio.tcc.config.security.UserSS;
import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado."));;

        return new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha());
    }
}
