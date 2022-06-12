package com.estacio.tcc.repository;

import com.estacio.tcc.builder.DevolutivaBuilder;
import com.estacio.tcc.builder.OrientacaoBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
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
@DisplayName("Devolutivas Repository")
@ActiveProfiles("test")
class DevolutivaRepositoryTest {

    @Autowired
    private DevolutivaRepository repository;

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Test
    @DisplayName("Salva devolutiva quando sucesso")
    void save_PersistDevolutiva_QuandoSucesso(){
        Orientador orientador = orientadorRepository.save(OrientadorBuilder.orientadorValido());

        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();

        orientacao.setOrientador(orientador);

        Orientacao orientacaoSalva = orientacaoRepository.save(orientacao);

        Devolutiva devolutiva = DevolutivaBuilder.criaDevolutiva();

        devolutiva.setOrientacao(orientacaoSalva);

        Devolutiva devolutivaSalva = repository.save(devolutiva);

        assertThat(devolutivaSalva).isNotNull();

        assertThat(devolutivaSalva.getId()).isNotNull();

        assertThat(devolutivaSalva.getOrientacao()).isEqualTo(devolutiva.getOrientacao());

        assertThat(devolutivaSalva.getDescricao()).isEqualTo(devolutiva.getDescricao());

        assertThat(devolutivaSalva.getLocalCorrecao()).isEqualTo(devolutiva.getLocalCorrecao());

        assertThat(devolutivaSalva.getVersaoDoc()).isEqualTo(devolutiva.getVersaoDoc());
    }

    @Test
    @DisplayName("Remove devolutiva quando sucesso")
    void delete_RemoveDevolutiva_QuandoSucesso(){
        Orientador orientador = orientadorRepository.save(OrientadorBuilder.orientadorValido());

        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();

        orientacao.setOrientador(orientador);

        Orientacao orientacaoSalva = orientacaoRepository.save(orientacao);

        Devolutiva devolutiva = DevolutivaBuilder.criaDevolutiva();

        devolutiva.setOrientacao(orientacaoSalva);

        Devolutiva devolutivaSalva = repository.save(devolutiva);

        repository.delete(devolutivaSalva);

        Optional<Devolutiva> devolutivaDeletada = repository.findById(devolutivaSalva.getId());

        assertThat(devolutivaDeletada).isEmpty();
    }

}