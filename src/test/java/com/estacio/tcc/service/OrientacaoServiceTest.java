package com.estacio.tcc.service;

import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.builder.OrientacaoBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.builderDTO.OrientacaoBuilderDTO;
import com.estacio.tcc.dto.OrientacaoDTO;
import com.estacio.tcc.dto.OrientacaoPostDTO;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.repository.EquipeRepository;
import com.estacio.tcc.repository.OrientacaoRepository;
import com.estacio.tcc.repository.OrientadorRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Orientacao Service")
public class OrientacaoServiceTest {

    @SpyBean
    private OrientacaoService service;

    @MockBean
    private OrientacaoRepository repository;

    @MockBean
    private OrientadorRepository orientadorRepository;

    @MockBean
    private EquipeRepository equipeRepository;

    @BeforeEach
    void setUp(){
        List<Orientacao> orientacaos = new ArrayList<>(List.of(OrientacaoBuilder.orientacaoValida()));

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of((OrientacaoBuilder.orientacaoValida())));
        BDDMockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                .thenReturn(orientacaos);
        BDDMockito.when(repository.save(ArgumentMatchers.any(Orientacao.class)))
                .thenReturn(OrientacaoBuilder.orientacaoValida());
        BDDMockito.when(orientadorRepository.findByMatricula(ArgumentMatchers.anyString()))
                .thenReturn(OrientadorBuilder.orientadorValido());
        BDDMockito.when(equipeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(EquipeBuilder.equipeValida()));
    }

    @Test
    @DisplayName("Lista filtrada quando sucesso")
    void listaFiltrada_RetornaListaDeOrientacao_QuandoSucesso(){
        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();

        List<OrientacaoDTO> list = service.lista(orientacao);

        assertThat(list).isNotNull();

        assertThat(list).isNotEmpty();

        assertThat(list.get(0).getDescricaoTCC()).isEqualTo(orientacao.getEstruturaTcc().getDescricao());
        assertThat(list.get(0).getDataOrientacao()).isEqualTo(orientacao.getDataOrientacao());
        assertThat(list.get(0).getTipoTCC()).isEqualTo(orientacao.getEstruturaTcc().getTipoTcc().getDescricao());
        assertThat(list.get(0).getMatriculaOrientador()).isEqualTo(orientacao.getOrientador().getMatricula());
        assertThat(list.get(0).getNomeOrientador()).isEqualTo(orientacao.getOrientador().getNome());
        assertThat(list.get(0).getId()).isEqualTo(orientacao.getId());
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraId_RetornaOrientacao_QuandoSucesso() {
        Long id = OrientacaoBuilder.orientacaoValida().getId();

        Orientacao resultado = service.encontrarId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = OrientacaoBuilder.orientacaoValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarId(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("EncontraIdDTO quando sucesso")
    void encontraIdDTO_RetornaOrientacao_QuandoSucesso() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of((OrientacaoBuilder.orientacaoValida2())));

        Orientacao orientacao = OrientacaoBuilder.orientacaoValida2();

        OrientacaoDTO resultado = service.encontrarIdDTO(orientacao.getId());

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(orientacao.getId());

        assertThat(resultado.getEquipe()).isEqualTo(orientacao.getEquipe());

        assertThat(resultado.getMatriculaOrientador()).isEqualTo(orientacao.getOrientador().getMatricula());

        assertThat(resultado.getNomeOrientador()).isEqualTo(orientacao.getOrientador().getNome());

        assertThat(resultado.getDataOrientacao()).isEqualTo(orientacao.getDataOrientacao());

        assertThat(resultado.getDescricaoTCC()).isEqualTo(orientacao.getEstruturaTcc().getDescricao());

        assertThat(resultado.getTipoTCC()).isEqualTo(orientacao.getEstruturaTcc().getTipoTcc().getDescricao());
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraIdDTO_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = OrientacaoBuilder.orientacaoValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarIdDTO(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Salva orientacao quando sucesso")
    void salva_SalvaOrientacao_QuandoSucesso(){
        BDDMockito.when(equipeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(EquipeBuilder.equipeValidaSemOrientacao()));

        OrientacaoPostDTO dto = OrientacaoBuilderDTO.criaOrientacaoDTO();

        Orientacao salvar = service.salvar(dto);

        assertThat(salvar).isNotNull().isEqualTo(OrientacaoBuilder.orientacaoValida());
    }

    @Test
    @DisplayName("Salva orientacao quando falhar")
    void salva_ErroAoSalvarQuandoEquipeJáPossuiOrientacao_QuandoSucesso(){
        OrientacaoPostDTO dto = OrientacaoBuilderDTO.criaOrientacaoDTO();

        catchThrowableOfType(() -> service.salvar(dto), RuleOfBusinessException.class);
    }

    @Test
    @DisplayName("Atualiza orientacao quando sucesso")
    void atualiza_SalvaOrientacaoAtualizada_QuandoSucesso(){
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(OrientacaoBuilder.orientacaoValida2()));

        OrientacaoPostDTO dto = OrientacaoBuilderDTO.atualizaOrientacaoPostDTO();

        Orientacao atualizar = service.atualizar(dto);

        assertThat(atualizar.getId()).isNotNull().isEqualTo(dto.getId());

        assertThat(atualizar.getDataOrientacao()).isEqualTo(dto.getDataOrientacao());

        assertThat(atualizar.getOrientador().getMatricula()).isEqualTo(dto.getMatriculaOrientador());

        assertThat(atualizar.getEstruturaTcc().getDescricao()).isEqualTo(dto.getDescricaoTCC());

        assertThat(atualizar.getEstruturaTcc().getTipoTcc().getDescricao()).isEqualTo(dto.getTipoTCC());

    }

    @Test
    @DisplayName("atualizar retorna erro se equipe for outra e já estiver orientação")
    void atualiza_RetornaErroQuandoEquipeForDiferenteEpossuiOrientação_QuandoFalhar() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(OrientacaoBuilder.orientacaoValida2()));

        BDDMockito.when(repository.saveAll(ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(OrientacaoBuilder.orientacaoValida(), OrientacaoBuilder.orientacaoValida()));

        OrientacaoPostDTO dto = OrientacaoBuilderDTO.atualizaOrientacaoPostDTO();
        dto.setEquipe(EquipeBuilder.equipeValida().getId());

        catchThrowableOfType(() -> service.atualizar(dto), RuleOfBusinessException.class);
    }

    @Test
    @DisplayName("atualizar retorna erro quando nao achar ID")
    void atualiza_RetornaErroQuandoNaoAcharId_QuandoFalhar() {
        OrientacaoPostDTO dto = OrientacaoBuilderDTO.atualizaOrientacaoPostDTO();

        catchThrowableOfType(() -> service.atualizar(dto), NullPointerException.class);
    }

    @Test
    @DisplayName("Deleta Orientacao quando sucesso")
    void deleta_DeletaOrientacao_QuandoSucesso(){
        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();
        BDDMockito.when(equipeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(EquipeBuilder.equipeValida()));

        BDDMockito.when(equipeRepository.save(ArgumentMatchers.any(Equipe.class)))
                .thenReturn(EquipeBuilder.equipeValidaSemOrientacao());

        service.deletar(orientacao);

        Mockito.verify(repository).delete(orientacao);
    }

    @Test
    @DisplayName("deleta de Orientação quando nao achar ID")
    void deleta_ErroAoDeletarQuandoNaoAcharIdDaOrientacao_QuandoFalhar(){
        Orientacao orientacao = OrientacaoBuilder.criaOrientacao();

        catchThrowableOfType(() -> service.deletar(orientacao), ObjectNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).delete(orientacao);
    }

    @Test
    @DisplayName("deleta orientação da erro quando nao achar equipe")
    void deleta_ErroAoDeletarQuandoNaoAcharEquipe_QuandoFalhar(){
        Orientacao orientacao = OrientacaoBuilder.orientacaoValida();

        orientacao.setEquipe(3l);

        catchThrowableOfType(() -> service.deletar(orientacao), ObjectNotFoundException.class);
    }

}
