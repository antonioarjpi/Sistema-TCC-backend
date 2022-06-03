package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevolutivaPostDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255, message = "Campo status tem que ter entre 3 a 255 caracteres")
    private String statusOrientacao;

    @NotNull
    @FutureOrPresent(message = "Data não pode ser menor que dia atual. ")
    private LocalDate dataMudanca;

    @NotNull
    private Long orientacaoId;

    @NotBlank
    @Size(min = 3, max = 255, message = "Descrição devolutiva tem que ter entre 3 a 255 caracteres")
    private String devolutivaDescricao;

    @NotBlank
    @Size(min = 3, max = 255, message = "Versão do documento tem que ter entre 3 a 255 caracteres")
    private String devolutivaVersaoDoc;

    @NotBlank
    @Size(min = 3, max = 255, message = "Local de correção tem que ter entre 3 a 255 caracteres")
    private String devolutivaLocalCorrecaoLocal;

    @NotBlank
    @Size(min = 3, max = 255, message = "Correção sugerida tem que ter entre 3 a 255 caracteres")
    private String devolutivaLocalCorrecaoCorrecaoSugerida;

}
