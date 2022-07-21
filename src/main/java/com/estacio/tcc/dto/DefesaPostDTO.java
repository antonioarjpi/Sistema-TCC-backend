package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefesaPostDTO {

    @NotNull(message = "É obrigatório informar a data da defesa")
    @FutureOrPresent(message = "Data banca menor que dia atual. ")
    public LocalDate data;

}
