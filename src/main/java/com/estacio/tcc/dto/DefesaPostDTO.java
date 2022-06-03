package com.estacio.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DefesaPostDTO {

    @NotNull
    @FutureOrPresent(message = "Data banca menor que dia atual. ")
    public Date data;

}
