package com.estacio.tcc.service;

import com.estacio.tcc.builder.AcompanhamentoBuilder;
import com.estacio.tcc.builder.OrientacaoBuilder;
import com.estacio.tcc.builderDTO.AcompanhamentoBuilderDTO;
import com.estacio.tcc.dto.DevolutivaDTO;
import com.estacio.tcc.dto.DevolutivaPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.repository.AcompanhamentoOrientacaoRepository;
import com.estacio.tcc.repository.OrientacaoRepository;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Orientacao Service")
public class DevolutivaServiceTest {

    @SpyBean
    private DevolutivaService service;

    @MockBean
    private AcompanhamentoOrientacaoRepository repository;

    @MockBean
    private OrientacaoRepository orientacaoRepository;

    @BeforeEach
    void setUp(){
        List<AcompanhamentoOrientacao> acompanhamento = new ArrayList<>(List.of(AcompanhamentoBuilder.acompanhamentoOrientacaoValido()));

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of((AcompanhamentoBuilder.acompanhamentoOrientacaoValido())));
        BDDMockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                .thenReturn(acompanhamento);
        BDDMockito.when(repository.save(ArgumentMatchers.any(AcompanhamentoOrientacao.class)))
                .thenReturn(AcompanhamentoBuilder.acompanhamentoOrientacaoValido());
        BDDMockito.when(orientacaoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(OrientacaoBuilder.orientacaoValida()));
    }

    @Test
    @DisplayName("Lista filtrada quando sucesso")
    void listaFiltrada_RetornaListaDeAcompanhamentos_QuandoSucesso(){
        AcompanhamentoOrientacao acompanhamento = AcompanhamentoBuilder.acompanhamentoOrientacaoValido();

        List<DevolutivaDTO> list = service.lista(acompanhamento);

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getId()).isEqualTo(acompanhamento.getId());
        assertThat(list.get(0).getOrientacaoId()).isEqualTo(acompanhamento.getOrientacao().getId());
        assertThat(list.get(0).getDataMudanca()).isEqualTo(acompanhamento.getDataMudanca());
        assertThat(list.get(0).getStatusOrientacao()).isEqualTo(acompanhamento.getStatusOrientacao());
        assertThat(list.get(0).getDevolutivaVersaoDoc()).isEqualTo(acompanhamento.getDevolutiva().getVersaoDoc());
        assertThat(list.get(0).getDevolutivaDescricao()).isEqualTo(acompanhamento.getDevolutiva().getDescricao());
        assertThat(list.get(0).getDevolutivaLocalCorrecaoLocal()).isEqualTo(acompanhamento.getDevolutiva().getLocalCorrecao().getLocal());
        assertThat(list.get(0).getDevolutivaLocalCorrecaoCorrecaoSugerida()).isEqualTo(acompanhamento.getDevolutiva().getLocalCorrecao().getCorrecaoSugerida());
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraId_RetornaAcompanhamento_QuandoSucesso() {
        Long id = AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getId();

        AcompanhamentoOrientacao resultado = service.encontrarId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarId(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("EncontraIdDTO quando sucesso")
    void encontraIdDTO_RetornaAcompanhamento_QuandoSucesso() {
        AcompanhamentoOrientacao acompanhamento = AcompanhamentoBuilder.acompanhamentoOrientacaoValido();

        DevolutivaDTO resultado = service.encontrarIdDTO(acompanhamento.getId());

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(acompanhamento.getId());
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraIdDTO_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = AcompanhamentoBuilder.acompanhamentoOrientacaoValido().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarIdDTO(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Salva acompanhamento quando sucesso")
    void salva_SalvaAcompanhamento_QuandoSucesso(){
        DevolutivaPostDTO dto = AcompanhamentoBuilderDTO.criaAcompanhamentoDTO();

        AcompanhamentoOrientacao salvar = service.salvar(dto);

        assertThat(salvar).isNotNull().isEqualTo(AcompanhamentoBuilder.acompanhamentoOrientacaoValido());
    }

    @Test
    @DisplayName("Salva acompanhamento quando falhar")
    void salva_ErroAoSalvarQuandoOrientacaoJaPossuirAcomapanhamento_QuandoSucesso(){
        DevolutivaPostDTO dto = AcompanhamentoBuilderDTO.criaAcompanhamentoDTO();

        catchThrowableOfType(() -> service.salvar(dto), RuleOfBusinessException.class);
    }

    @Test
    @DisplayName("Atualiza acompanhamento quando sucesso")
    void atualiza_SalvaAcompanhamentoAtualizado_QuandoSucesso(){
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AcompanhamentoBuilder.acompanhamentoOrientacaoValido()));

        DevolutivaPostDTO dto = AcompanhamentoBuilderDTO.atualizaAcompanhamentoDTO();

        AcompanhamentoOrientacao atualizar = service.atualizar(dto);

        assertThat(atualizar).isNotNull();

        assertThat(atualizar.getId()).isEqualTo(dto.getId());

        assertThat(atualizar.getOrientacao().getId()).isEqualTo(dto.getOrientacaoId());

        assertThat(atualizar.getDataMudanca()).isEqualTo(dto.getDataMudanca());

        assertThat(atualizar.getStatusOrientacao()).isEqualTo(dto.getStatusOrientacao());

        assertThat(atualizar.getDevolutiva().getVersaoDoc()).isEqualTo(dto.getDevolutivaVersaoDoc());

        assertThat(atualizar.getDevolutiva().getDescricao()).isEqualTo(dto.getDevolutivaDescricao());

        assertThat(atualizar.getDevolutiva().getLocalCorrecao().getLocal()).isEqualTo(dto.getDevolutivaLocalCorrecaoLocal());

        assertThat(atualizar.getDevolutiva().getLocalCorrecao().getCorrecaoSugerida()).isEqualTo(dto.getDevolutivaLocalCorrecaoCorrecaoSugerida());

    }

    @Test
    @DisplayName("atualizar retorna erro quando não achar ID de acompanhamento")
    void atualiza_RetornaErroQuandoNaoEncontrarIdDoAcompanhamento_QuandoFalhar() {
        DevolutivaPostDTO dto = AcompanhamentoBuilderDTO.atualizaAcompanhamentoDTO();

        catchThrowableOfType(() -> service.atualizar(dto), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Deleta Acompanhamento quando sucesso")
    void deleta_DeletaAcompanhamento_QuandoSucesso(){
        AcompanhamentoOrientacao acompanhamento = AcompanhamentoBuilder.acompanhamentoOrientacaoValido();

        service.deletar(acompanhamento);

        Mockito.verify(repository).delete(acompanhamento);
    }

    @Test
    @DisplayName("deleta acompanhamento quando nao achar ID")
    void deleta_ErroAoDeletarQuandoNaoAcharIdDoAcompanhamento_QuandoFalhar(){
        AcompanhamentoOrientacao acompanhamento = AcompanhamentoBuilder.criaAcompanhamento();

        catchThrowableOfType(() -> service.deletar(acompanhamento), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).delete(acompanhamento);
    }

}
