package br.com.estoque.veiculos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModeloRequestDTO(
        @NotBlank(message = "O nome do modelo e obrigatorio")
        @Size(max = 80, message = "O nome do modelo deve ter no maximo 80 caracteres")
        String nome,

        @NotBlank(message = "A marca do modelo e obrigatoria")
        @Size(max = 80, message = "A marca deve ter no maximo 80 caracteres")
        String marca
) {
}
