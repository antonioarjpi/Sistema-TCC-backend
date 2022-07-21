package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DevolutivaPostDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar o status de orientação")
    @Size(min = 3, max = 255, message = "Campo status tem que ter entre 3 a 255 caracteres")
    private String statusOrientacao;

    @NotNull(message = "É obrigatório informar a data de mudança")
    private LocalDate dataMudanca;

    @NotNull(message = "É obrigatório informar código de orientação ")
    private Long orientacaoId;

    @NotBlank(message = "É obrigatório informar a descrição da devolutiva ")
    @Size(min = 3, max = 255, message = "Descrição devolutiva tem que ter entre 3 a 255 caracteres")
    private String devolutivaDescricao;

    @NotBlank(message = "É obrigatório informar a versão do documento ")
    @Size(min = 3, max = 255, message = "Versão do documento tem que ter entre 3 a 255 caracteres")
    private String devolutivaVersaoDoc;

    @NotBlank(message = "É obrigatório informar local de correção ")
    @Size(min = 3, max = 255, message = "Local de correção tem que ter entre 3 a 255 caracteres")
    private String devolutivaLocalCorrecaoLocal;

    @NotBlank(message = "É obrigatório informar a correção sugerida ")
    @Size(min = 3, max = 255, message = "Correção sugerida tem que ter entre 3 a 255 caracteres")
    private String devolutivaLocalCorrecaoCorrecaoSugerida;

}
