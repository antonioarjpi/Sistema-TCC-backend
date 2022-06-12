package com.estacio.tcc.repository;

import com.estacio.tcc.builder.OrientacaoBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.model.Orientador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Orientação repository")
@ActiveProfiles("test")
class OrientacaoRepositoryTest {

    @Autowired
    private OrientacaoRepository repository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Test
    @DisplayName("Salva orientação quando sucesso")
    void save_PeresistOrientação_QuandoSucesso(){
        Orientador orientador = orientadorRepository.save(OrientadorBuilder.orientadorValido());

        Orientacao orientacao = OrientacaoBuilder.criaOrientacao();

        orientacao.setOrientador(orientador);

        Orientacao orientacaoSalva = repository.save(orientacao);

        assertThat(orientacaoSalva).isNotNull();

        assertThat(orientacaoSalva.getId()).isNotNull();

        assertThat(orientacaoSalva.getDataOrientacao()).isEqualTo(orientacao.getDataOrientacao());

        assertThat(orientacaoSalva.getOrientador()).isEqualTo(orientacao.getOrientador());

        assertThat(orientacao.getAcompanhamentoOrientacao()).isEqualTo(orientacao.getAcompanhamentoOrientacao());

        assertThat(orientacao.getEquipe()).isEqualTo(orientacao.getEquipe());

        assertThat(orientacao.getEstruturaTcc()).isEqualTo(orientacao.getEstruturaTcc());
    }

    @Test
    @DisplayName("Remove orientação quando sucesso")
    void delete_RemoveOrientação_QuandoSucesso(){
        Orientador orientador = orientadorRepository.save(OrientadorBuilder.orientadorValido());

        Orientacao orientacao = OrientacaoBuilder.criaOrientacao();

        orientacao.setOrientador(orientador);

        Orientacao orientacaoSalva = repository.save(orientacao);

        repository.delete(orientacaoSalva);

        Optional<Orientacao> orientacaoDeletada = repository.findById(orientacaoSalva.getId());

        assertThat(orientacaoDeletada).isEmpty();

    }

}