package com.estacio.tcc.service;

import com.estacio.tcc.builder.UsuarioBuilder;
import com.estacio.tcc.model.Usuario;
import com.estacio.tcc.repository.UsuarioRepository;
import com.estacio.tcc.service.exceptions.AuthenticateErrorException;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DisplayName("UsuarioService")
class UsuarioServiceTest {

    @SpyBean
    private UsuarioService service;

    @MockBean
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        Mockito.when(repository.findById(UsuarioBuilder.criaUsuario().getId()))
                .thenReturn(Optional.of(UsuarioBuilder.criaUsuario()));
    }

    @Test
    void salva_DeveSalvarUmUsuario_QuandoSucesso() {
        Mockito.doNothing().when(service).validaEmail(Mockito.anyString());
        Usuario usuario = Usuario
                .builder()
                .id(1l)
                .nome("nome")
                .email("email@email.com")
                .senha(passwordEncoder.encode("senha1"))
                .build();

        Mockito.doNothing().when(service).criptografarSenha(Mockito.any());

        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioSalvo = service.salva(new Usuario());

        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    void salva_naoDeveSalvarUsuarioComEmailJaCadastrado(){
        Assertions.assertThrows(RuleOfBusinessException.class, () -> {
            Usuario user = UsuarioBuilder.criaUsuario();

            Mockito.doThrow(RuleOfBusinessException.class).when(service).validaEmail(user.getEmail());

            service.salva(user);

            Mockito.verify(repository, Mockito.never()).save(user);
        });
    }

    @Test
    void encontraId_RetornaUsuarioQuandoEncontrarId_QuandoSucesso() {
        Long id = UsuarioBuilder.criaUsuario().getId();

        Usuario result = service.encontraId(id);

        assertThat(result).isNotNull();

        assertThat(result.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    void encontraId_RetornaErroObjectNotFound_QuandoNaoEncontrarUsuario() {
        Long id = UsuarioBuilder.criaUsuario().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontraId(id), ObjectNotFoundException.class);
    }

    @Test
    void autentica_DeveAutenticar_QuandoSucesso() {
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(passwordEncoder.encode(senha)).id(1l).build();

        Mockito.when(repository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Usuario result = service.autentica(email, senha);

        assertThat(result).isNotNull();
    }

    @Test
    public void autentica_deveLancarErro_QuandoNaoEncontraEmail() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> service.autentica("email@email.com", "senha") );

        assertThat(exception)
                .isInstanceOf(AuthenticateErrorException.class)
                .hasMessage("Usuário não encontrado.");
    }

    @Test
    public void autentica_deveLancarErro_QuandoSenhaForIncorreta() {
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        Throwable exception = catchThrowable( () ->  service.autentica("email@email.com", "123") );
        assertThat(exception).isInstanceOf(AuthenticateErrorException.class).hasMessage("Senha incorreta.");
    }

    @Test
    void validaEmail_DeveValidarEmail_QuandoSucesso() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        service.validaEmail("email@email.com");
    }

    @Test
    void validaEmail_DeveRetornarErro_QuandoJaExistirEmailCadastrado() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        org.junit.jupiter.api.Assertions
                .assertThrows(RuleOfBusinessException.class, () -> service.validaEmail("email@email.com"));
    }
}