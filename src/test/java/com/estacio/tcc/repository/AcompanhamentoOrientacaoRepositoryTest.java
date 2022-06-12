package com.estacio.tcc.repository;

import com.estacio.tcc.builder.AcompanhamentoBuilder;
import com.estacio.tcc.builder.DevolutivaBuilder;
import com.estacio.tcc.builder.OrientacaoBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Devolutiva;
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
@DisplayName("Acompanhamento repository")
@ActiveProfiles("test")
class AcompanhamentoOrientacaoRepositoryTest {

    @Autowired
    private AcompanhamentoOrientacaoRepository repository;

    @Autowired
    private DevolutivaRepository devolutivaRepository;

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Test
    @DisplayName("Salvar acompanhamento de devolutivas")
    void save_PersistAcompanhamento_QuandoSucesso(){
        Orientador orientador = orientadorRepository.save(OrientadorBuilder.orientadorValido());

        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();

        orientacao.setOrientador(orientador);

        Orientacao orientacaoSalva = orientacaoRepository.save(orientacao);

        AcompanhamentoOrientacao acompanhamentoOrientacao = AcompanhamentoBuilder.criaAcompanhamento();

        acompanhamentoOrientacao.setOrientacao(orientacaoSalva);

        AcompanhamentoOrientacao acompanhamentoSalvo = repository.save(acompanhamentoOrientacao);

        assertThat(acompanhamentoSalvo).isNotNull();

        assertThat(acompanhamentoSalvo.getId()).isNotNull();

        assertThat(acompanhamentoSalvo.getStatusOrientacao()).isEqualTo(acompanhamentoOrientacao.getStatusOrientacao());

        assertThat(acompanhamentoSalvo.getDataMudanca()).isEqualTo(acompanhamentoOrientacao.getDataMudanca());

        assertThat(acompanhamentoSalvo.getOrientacao()).isEqualTo(acompanhamentoOrientacao.getOrientacao());

        assertThat(acompanhamentoSalvo.getDevolutiva()).isEqualTo(acompanhamentoOrientacao.getDevolutiva());
    }

    @Test
    @DisplayName("Remove Acompanhamento de devolução")
    void delete_RemoveAcompanhamento_QuandoSucesso(){
        Orientador orientador = orientadorRepository.save(OrientadorBuilder.orientadorValido());

        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();

        orientacao.setOrientador(orientador);

        Orientacao orientacaoSalva = orientacaoRepository.save(orientacao);

        AcompanhamentoOrientacao acompanhamentoOrientacao = AcompanhamentoBuilder.criaAcompanhamento();

        acompanhamentoOrientacao.setOrientacao(orientacaoSalva);

        AcompanhamentoOrientacao acompanhamentoSalvo = repository.save(acompanhamentoOrientacao);

        repository.delete(acompanhamentoSalvo);

        Optional<AcompanhamentoOrientacao> acompanhamentoDeletado = repository.findById(acompanhamentoOrientacao.getId());

        assertThat(acompanhamentoDeletado).isEmpty();

    }

}