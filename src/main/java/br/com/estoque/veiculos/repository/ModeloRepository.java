package br.com.estoque.veiculos.repository;

import br.com.estoque.veiculos.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    Optional<Modelo> findByNomeIgnoreCaseAndMarca_NomeIgnoreCase(String nome, String marca);

    boolean existsByNomeIgnoreCaseAndMarca_NomeIgnoreCase(String nome, String marca);

    List<Modelo> findByMarcaId(Long marcaId);
}
