package com.estacio.tcc.repository;

import com.estacio.tcc.builder.BancaBuilder;
import com.estacio.tcc.model.Banca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Banca repository")
@ActiveProfiles("test")
class BancaRepositoryTest {

    @Autowired
    private BancaRepository repository;

    @Test
    @DisplayName("Persist Equipe quando for suecsso")
    void save_PersistBanca_QuandoSucesso(){
        Banca banca = BancaBuilder.criaBanca();

        Banca bancaSalva = repository.save(banca);

        assertThat(bancaSalva).isNotNull();

        assertThat(bancaSalva.getId()).isNotNull();

        assertThat(bancaSalva.getDataBanca()).isEqualTo(banca.getDataBanca());

        assertThat(bancaSalva.getMembro()).isEqualTo(banca.getMembro());

        assertThat(bancaSalva.getDescricao()).isEqualTo(banca.getDefesa());

        assertThat(bancaSalva.getOrdemApresentacao()).isEqualTo(banca.getOrdemApresentacao());

    }

    @Test
    @DisplayName("Remove banca quando sucesso")
    void delete_RemoveBanca_QuandoSucesso(){
        Banca banca = BancaBuilder.criaBanca();

        Banca bancaSalva = repository.save(banca);

        repository.delete(bancaSalva);

        Optional<Banca> bancaDeletada = repository.findById(bancaSalva.getId());

        assertThat(bancaDeletada).isEmpty();
    }


}