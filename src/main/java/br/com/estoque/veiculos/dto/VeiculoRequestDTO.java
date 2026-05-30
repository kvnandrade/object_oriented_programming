package br.com.estoque.veiculos.dto;

import br.com.estoque.veiculos.entity.StatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VeiculoRequestDTO(
        @NotBlank(message = "O modelo e obrigatorio")
        @Size(max = 80, message = "O modelo deve ter no maximo 80 caracteres")
        String modelo,

        @NotBlank(message = "A marca e obrigatoria")
        @Size(max = 80, message = "A marca deve ter no maximo 80 caracteres")
        String marca,

        @NotNull(message = "O ano e obrigatorio")
        @Min(value = 1886, message = "O ano deve ser maior ou igual a 1886")
        @Max(value = 2100, message = "O ano deve ser menor ou igual a 2100")
        Integer ano,

        @NotBlank(message = "A cor e obrigatoria")
        @Size(max = 40, message = "A cor deve ter no maximo 40 caracteres")
        String cor,

        @NotNull(message = "O preco e obrigatorio")
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quilometragem e obrigatoria")
        @Min(value = 0, message = "A quilometragem nao pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "O status e obrigatorio")
        StatusVeiculo status
) {
}
