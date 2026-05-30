package br.com.estoque.veiculos.dto;

import br.com.estoque.veiculos.entity.Marca;

public record MarcaResponseDTO(
        Long id,
        String nome
) {
    public static MarcaResponseDTO fromEntity(Marca marca) {
        return new MarcaResponseDTO(marca.getId(), marca.getNome());
    }
}
