package com.estacio.tcc.repository;

import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.LinhaPesquisa;
import com.estacio.tcc.model.Tema;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Equipe Repository")
@ActiveProfiles("test")
class EquipeRepositoryTest {

    @Autowired
    private EquipeRepository repository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @DisplayName("Persist Equipe quando for suecsso")
    void save_PersistEquipe_QuandoSucesso(){
        Equipe equipe = EquipeBuilder.criaEquipe();

        Equipe equipeSalva = repository.save(equipe);

        assertThat(equipeSalva).isNotNull();

        assertThat(equipeSalva.getId()).isNotNull();

        assertThat(equipeSalva.getNome()).isEqualTo(equipe.getNome());

        assertThat(equipeSalva.getQuantidade()).isEqualTo(equipe.getQuantidade());

        assertThat(equipeSalva.getDataCadastro()).isEqualTo(equipe.getDataCadastro());

        assertThat(equipeSalva.getTema()).isEqualTo(equipe.getTema());

        assertThat(equipeSalva.getAlunos()).isEqualTo(equipe.getAlunos());

    }

    @Test
    @DisplayName("Remove equipe quando sucesso")
    void delete_RemoveEquipe_QuandoSucesso(){
        Equipe equipe = EquipeBuilder.criaEquipe();

        Equipe equipeSalva = repository.save(equipe);

        repository.delete(equipeSalva);

        Optional<Equipe> equipeDeletada = repository.findById(equipeSalva.getId());

        assertThat(equipeDeletada).isEmpty();
    }

    @Test
    @DisplayName("Atualiza equipe quando for suecsso")
    void saveUpdate_PersistEquipe_QuandoSucesso() {
        Equipe equipe = EquipeBuilder.criaEquipe();

        Equipe equipeSalva = repository.save(equipe);

        Equipe equipeAtualizada = repository.findById(equipeSalva.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Não encontrado"));

        equipeAtualizada.setId(equipeAtualizada.getId());
        equipeAtualizada.setNome("Nome atualizado");
        equipeAtualizada.setQuantidade(2);
        equipeAtualizada.setDataCadastro(LocalDate.now());
        equipeAtualizada.setAlunos(null);
        equipeAtualizada.setTema(Tema
                .builder()
                .id(equipeAtualizada.getTema().getId())
                .delimitacao("Nova delimitação")
                .linhaPesquisa(
                        LinhaPesquisa
                                .builder()
                                .id(equipeAtualizada.getTema().getLinhaPesquisa().getId())
                                .descricao("Nova descrição")
                                .build())
                .build());

        Equipe atualizado = repository.save(equipeAtualizada);

        assertThat(atualizado).isEqualTo(equipeAtualizada);

        assertThat(atualizado.getId()).isEqualTo(equipe.getId());

        assertThat(atualizado.getNome()).isEqualTo(equipe.getNome());

        assertThat(atualizado.getTema()).isEqualTo(equipe.getTema());

        assertThat(atualizado.getQuantidade()).isEqualTo(equipe.getQuantidade());

        assertThat(atualizado.getDataCadastro()).isEqualTo(atualizado.getDataCadastro());

        assertThat(atualizado.getOrientacao()).isEqualTo(atualizado.getOrientacao());
    }
}