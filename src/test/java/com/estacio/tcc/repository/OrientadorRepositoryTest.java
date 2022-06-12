package com.estacio.tcc.repository;

import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.model.AreaConhecimento;
import com.estacio.tcc.model.LinhaPesquisa;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.Titulacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Orientador Repository")
@ActiveProfiles("test")
class OrientadorRepositoryTest {

    @Autowired
    private OrientadorRepository repository;

    @Test
    @DisplayName("Retorna orientador quando achar matrícula")
    void findByMatricula_RetornaOrientador_QuandoSucesso() {
        Orientador orientador = OrientadorBuilder.criaOrientador();
        Orientador orientadorSalvo = repository.save(orientador);

        Orientador matricula = repository.findByMatricula(orientadorSalvo.getMatricula());

        assertThat(matricula).isEqualTo(orientador);
    }

    @Test
    @DisplayName("Retorna nulo quando não achar matrícula")
    void findByMatricula_RetornaNulo_QuandoFalhar() {
        Orientador orientador = OrientadorBuilder.criaOrientador();

        repository.save(orientador);

        Orientador matricula = repository.findByMatricula("Matricula inexistente");

        assertThat(matricula).isNull();
    }

    @Test
    @DisplayName("Retorna verdadeiro se email existir")
    void existsByEmail_RetornaVerdadeiro_QuandoSucesso() {
        Orientador orientador = OrientadorBuilder.criaOrientador();

        Orientador orientadorSalvo = repository.save(orientador);

        boolean existsByEmail = repository.existsByEmail(orientadorSalvo.getEmail());

        assertThat(existsByEmail).isTrue();
    }

    @Test
    @DisplayName("Retorna falso se email não existir")
    void existsByEmail_RetornaFalso_QuandoNãoEncontrar() {
        Orientador orientador = OrientadorBuilder.criaOrientador();

        repository.save(orientador);

        boolean existsByEmail = repository.existsByEmail("Matrícula não existe");

        assertThat(existsByEmail).isFalse();
    }

    @Test
    @DisplayName("Retorna verdadeiro se matrícula existir")
    void existsByMatricula_RetornaVerdadeiro_QuandoSucesso() {
        Orientador orientador = OrientadorBuilder.criaOrientador();

        Orientador orientadorSalvo = repository.save(orientador);

        boolean existsByMatricula = repository.existsByMatricula(orientadorSalvo.getMatricula());

        assertThat(existsByMatricula).isTrue();
    }

    @Test
    @DisplayName("Retorna falso se matricula não existir")
    void existsByMatricula_RetornaFalso_QuandoNãoEncontrar() {
        boolean existsByMatricula = repository.existsByMatricula("Matrícula não existe");

        assertThat(existsByMatricula).isFalse();
    }

    @Test
    @DisplayName("Persist Orintador quando for suecsso")
    void save_PersistOrientador_QuandoSucesso(){
        Orientador orientador = OrientadorBuilder.criaOrientador();

        Orientador orientadorSalvo = repository.save(orientador);

        assertThat(orientadorSalvo).isNotNull();

        assertThat(orientadorSalvo.getId()).isNotNull();

        assertThat(orientadorSalvo.getNome()).isEqualTo(orientador.getNome());

        assertThat(orientadorSalvo.getEmail()).isEqualTo(orientador.getEmail());

        assertThat(orientadorSalvo.getMatricula()).isEqualTo(orientador.getMatricula());

        assertThat(orientadorSalvo.getImagem()).isEqualTo(orientador.getImagem());

        assertThat(orientadorSalvo.getTitulacao()).isEqualTo(orientador.getTitulacao());

        assertThat(orientador.getLinhaPesquisa()).isEqualTo(orientador.getLinhaPesquisa());

        assertThat(orientador.getLinhaPesquisa().getAreaConhecimento())
                .isEqualTo(orientador.getLinhaPesquisa().getAreaConhecimento());

    }

    @Test
    @DisplayName("Remove Orientador quando sucesso")
    void delete_RemoveOrientador_QuandoSucesso(){
        Orientador orientadorSalvo = repository.save(OrientadorBuilder.criaOrientador());

        repository.delete(orientadorSalvo);

        Optional<Orientador> orientador = repository.findById(orientadorSalvo.getId());

        assertThat(orientador).isEmpty();
    }

    @Test
    @DisplayName("Atualiza Orientador quando sucesso")
    void saveUpdate_AtualizaOrientador_QuandoSucesso(){
        Orientador orientador = OrientadorBuilder.criaOrientador();

        Orientador orientadorSalvo = repository.save(orientador);

        Optional<Orientador> novoOrientador = repository.findById(orientadorSalvo.getId());
        novoOrientador.get().setId(novoOrientador.get().getId());
        novoOrientador.get().setNome("Novo Nome");
        novoOrientador.get().setEmail("novoemail@email.com");
        novoOrientador.get().setSenha("novaSenha");
        novoOrientador.get().setMatricula("nova matricula");
        novoOrientador.get().setImagem("novaimagem.png");
        novoOrientador.get().setTitulacao(Titulacao
                .builder()
                .id(novoOrientador.get().getTitulacao().getId())
                .grau("Novo Grau")
                .ies("Nova IES")
                .descricao("Nova descricao")
                .build());
        novoOrientador.get().setLinhaPesquisa(LinhaPesquisa
                .builder()
                        .id(novoOrientador.get().getLinhaPesquisa().getId())
                        .descricao("Nova descrição de linha")
                        .areaConhecimento(AreaConhecimento
                                .builder()
                                .id(novoOrientador.get().getLinhaPesquisa().getAreaConhecimento().getId())
                                .descricao("Nova descrição de conhecimento")
                                .build())
                .build());

        Orientador atualizado = repository.save(novoOrientador.get());

        assertThat(atualizado).isNotNull();

        assertThat(atualizado.getId()).isEqualTo(orientador.getId());

        assertThat(atualizado.getNome()).isEqualTo(orientador.getNome());

        assertThat(atualizado.getEmail()).isEqualTo(orientador.getEmail());

        assertThat(atualizado.getMatricula()).isEqualTo(orientador.getMatricula());

        assertThat(atualizado.getImagem()).isEqualTo(orientador.getImagem());

        assertThat(atualizado.getSenha()).isEqualTo(orientador.getSenha());

        assertThat(atualizado.getTitulacao()).isEqualTo(atualizado.getTitulacao());

        assertThat(atualizado.getLinhaPesquisa()).isEqualTo(atualizado.getLinhaPesquisa());
    }


}