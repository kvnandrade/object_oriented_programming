package br.com.estoque.veiculos.dto;

import br.com.estoque.veiculos.entity.StatusVeiculo;
import br.com.estoque.veiculos.entity.Veiculo;

import java.math.BigDecimal;

public record VeiculoResponseDTO(
        Long id,
        String modelo,
        String marca,
        Integer ano,
        String cor,
        BigDecimal preco,
        Integer quilometragem,
        StatusVeiculo status
) {
    public static VeiculoResponseDTO fromEntity(Veiculo veiculo) {
        return new VeiculoResponseDTO(
                veiculo.getId(),
                veiculo.getModelo(),
                veiculo.getMarca(),
                veiculo.getAno(),
                veiculo.getCor(),
                veiculo.getPreco(),
                veiculo.getQuilometragem(),
                veiculo.getStatus()
        );
    }
}
