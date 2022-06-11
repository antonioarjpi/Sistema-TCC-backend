package com.estacio.tcc.repository;

import com.estacio.tcc.builder.AlunoBuilder;
import com.estacio.tcc.model.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("AlunoRepository")
@ActiveProfiles("test")
class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository repository;

    @Test
    @DisplayName("Retorna aluno, quando sucesso")
    void findByEmail_RetornaAluno_QuandoForSucesso() {
        Aluno aluno = AlunoBuilder.criaAluno();

        Aluno save = repository.save(aluno);

        Aluno procura = repository.findByEmail(save.getEmail());

        assertThat(procura).isEqualTo(aluno);
    }

    @Test
    @DisplayName("Retorna nulo quando e-mail de aluno não existir")
    void findByEmail_RetornaNulo_QuandoForSucesso() {
        Aluno procura = repository.findByEmail("email@naoexiste.com");

        assertThat(procura).isNull();
    }

    @Test
    @DisplayName("Retorna nulo quando matricula de aluno não existir")
    void findByMatricula_RetornaNulo_QuandoForSucesso() {
        Aluno procura = repository.findByMatricula("20222002");

        assertThat(procura).isNull();
    }

    @Test
    @DisplayName("Retorna aluno quando existir matricula, quando sucesso")
    void findByMatricula_RetornaAluno_QuandoForSucesso() {
        Aluno aluno = AlunoBuilder.criaAluno();

        Aluno save = repository.save(aluno);

        Aluno procura = repository.findByMatricula(save.getMatricula());

        assertThat(procura).isEqualTo(aluno);
    }

    @Test
    @DisplayName("Retorna verdadeiro quando for sucesso")
    void existsByEmail_RetornaVerdadeiro_QuandoSucesso() {
        Aluno aluno = repository.save(AlunoBuilder.criaAluno());

        boolean exists = repository.existsByEmail(aluno.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Retorna falso quando não existir e-mail")
    void existsByEmail_RetornaFalso_QuandoSucesso() {
        Aluno aluno = repository.save(AlunoBuilder.criaAluno());

        repository.deleteAll();

        boolean exists = repository.existsByEmail(aluno.getEmail());

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Retorna verdadeiro quando for sucesso")
    void existsByMatricula_RetornaVerdadeiro_QuandoSucesso() {
        Aluno aluno = repository.save(AlunoBuilder.criaAluno());

        boolean exists = repository.existsByMatricula(aluno.getMatricula());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Retorna falso quando não existir e-mail")
    void existsByMatricula_RetornaFalso_QuandoSucesso() {
        Aluno aluno = repository.save(AlunoBuilder.criaAluno());

        repository.deleteAll();

        boolean exists = repository.existsByMatricula(aluno.getMatricula());

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Persist aluno quando for suecsso")
    void save_RetornaAlunoSalvo_QuandoSucesso(){
        Aluno aluno = AlunoBuilder.criaAluno();

        Aluno save = repository.save(aluno);

        assertThat(save).isNotNull();

        assertThat(save.getId()).isNotNull();

        assertThat(save.getNome()).isEqualTo(aluno.getNome());

        assertThat(save.getEmail()).isEqualTo(aluno.getEmail());

        assertThat(save.getMatricula()).isEqualTo(aluno.getMatricula());

        assertThat(save.getImagem()).isEqualTo(aluno.getImagem());

        assertThat(save.getSenha()).isEqualTo(aluno.getSenha());

    }

    @Test
    @DisplayName("Remove aluno quando sucesso")
    void delete_RemoveAluno_QuandoSucesso(){
        Aluno aluno = repository.save(AlunoBuilder.criaAluno());

        repository.delete(aluno);

        Optional<Aluno> alunoDeletado = repository.findById(aluno.getId());

        assertThat(alunoDeletado).isEmpty();
    }

    @Test
    @DisplayName("Atualiza aluno quando for sucesso")
    void update_AtualizaAluno_QuandoSuceso(){
        Aluno aluno = AlunoBuilder.criaAluno();

        Aluno novoAluno = repository.save(aluno);

        novoAluno.setNome("atualizado");
        novoAluno.setEmail("atualizado@email.com");
        novoAluno.setMatricula("atualiza");
        novoAluno.setSenha("atualizado");
        novoAluno.setImagem("atualizado.png");

        Aluno atualizado = repository.save(novoAluno);

        assertThat(atualizado).isNotNull();

        assertThat(atualizado.getId()).isNotNull();

        assertThat(atualizado.getId()).isEqualTo(aluno.getId());

        assertThat(atualizado).isEqualTo(aluno);
    }

}