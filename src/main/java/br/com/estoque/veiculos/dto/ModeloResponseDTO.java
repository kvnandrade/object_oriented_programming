package br.com.estoque.veiculos.dto;

import br.com.estoque.veiculos.entity.Modelo;

public record ModeloResponseDTO(
        Long id,
        String nome,
        Long marcaId,
        String marca
) {
    public static ModeloResponseDTO fromEntity(Modelo modelo) {
        return new ModeloResponseDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getMarca().getId(),
                modelo.getMarca().getNome()
        );
    }
}
