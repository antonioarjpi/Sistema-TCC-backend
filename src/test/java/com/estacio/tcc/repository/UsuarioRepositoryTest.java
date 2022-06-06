package com.estacio.tcc.repository;

import com.estacio.tcc.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UsuarioRepository")
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    @DisplayName("Retorna usuário, quando sucesso")
    void findByEmail_RetornaUsuario_QuandoForSucesso() {
        Usuario usuario = criaUsuario();

        Usuario save = repository.save(usuario);

        Optional<Usuario> procura = repository.findByEmail(save.getEmail());

        assertThat(procura.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Retorna nulo quando e-mail não existe")
    void findByEmail_RetornaNulo_QuandoForSucesso() {
        Optional<Usuario> procura = repository.findByEmail("email@naoexiste.com");

        assertThat(procura.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Retorna verdadeiro quando for sucesso")
    void existsByEmail_RetornaVerdadeiro_QuandoSucesso() {
        Usuario usuario = repository.save(criaUsuario());

        boolean exists = repository.existsByEmail(usuario.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Retorna falso quando não existir e-mail")
    void existsByEmail_RetornaFalso_QuandoSucesso() {
        Usuario usuario = repository.save(criaUsuario());

        repository.deleteAll();

        boolean exists = repository.existsByEmail(usuario.getEmail());

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Persist usuário quando for sucesso")
    void save_PersistUsuario_QuandoSucesso(){
        Usuario usuarioCriado = criaUsuario();

        Usuario usarioSalvo = repository.save(usuarioCriado);

        assertThat(usarioSalvo).isNotNull();

        assertThat(usarioSalvo.getId()).isNotNull();

        assertThat(usarioSalvo.getNome()).isEqualTo(usuarioCriado.getNome());

        assertThat(usarioSalvo.getEmail()).isEqualTo(usuarioCriado.getEmail());

        assertThat(usarioSalvo.getSenha()).isEqualTo(usuarioCriado.getSenha());
    }

    private Usuario criaUsuario(){
        return Usuario.builder()
                .nome("Teste Teste")
                .email("teste@teste.com")
                .senha("123456")
                .build();
    }
}