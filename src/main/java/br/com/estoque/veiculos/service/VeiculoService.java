package br.com.estoque.veiculos.service;

import br.com.estoque.veiculos.dto.VeiculoRequestDTO;
import br.com.estoque.veiculos.dto.VeiculoResponseDTO;
import br.com.estoque.veiculos.entity.StatusVeiculo;
import br.com.estoque.veiculos.entity.Veiculo;
import br.com.estoque.veiculos.exception.RecursoNaoEncontradoException;
import br.com.estoque.veiculos.repository.VeiculoRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional
    public VeiculoResponseDTO cadastrar(VeiculoRequestDTO dto) {
        Veiculo veiculo = Veiculo.criar(dto);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
        return VeiculoResponseDTO.fromEntity(veiculoSalvo);
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listar() {
        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public VeiculoResponseDTO buscarPorId(Long id) {
        return VeiculoResponseDTO.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto) {
        Veiculo veiculo = buscarEntidadePorId(id);
        veiculo.atualizar(dto);
        return VeiculoResponseDTO.fromEntity(veiculo);
    }

    @Transactional
    public void excluir(Long id) {
        Veiculo veiculo = buscarEntidadePorId(id);
        veiculoRepository.delete(veiculo);
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> filtrar(String marca, String modelo, Integer ano,
                                            BigDecimal precoMin, BigDecimal precoMax,
                                            StatusVeiculo status) {
        validarFaixaDePreco(precoMin, precoMax);
        Specification<Veiculo> filtros = montarFiltros(marca, modelo, ano, precoMin, precoMax, status);

        return veiculoRepository.findAll(filtros)
                .stream()
                .map(VeiculoResponseDTO::fromEntity)
                .toList();
    }

    private Veiculo buscarEntidadePorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo com id " + id + " nao encontrado"));
    }

    private void validarFaixaDePreco(BigDecimal precoMin, BigDecimal precoMax) {
        if (precoMin != null && precoMax != null && precoMin.compareTo(precoMax) > 0) {
            throw new IllegalArgumentException("O precoMin nao pode ser maior que o precoMax");
        }
    }

    private Specification<Veiculo> montarFiltros(String marca, String modelo, Integer ano,
                                                 BigDecimal precoMin, BigDecimal precoMax,
                                                 StatusVeiculo status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(marca)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("marca")),
                        "%" + marca.toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(modelo)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("modelo")),
                        "%" + modelo.toLowerCase() + "%"
                ));
            }

            if (ano != null) {
                predicates.add(criteriaBuilder.equal(root.get("ano"), ano));
            }

            if (precoMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<BigDecimal>get("preco"), precoMin));
            }

            if (precoMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<BigDecimal>get("preco"), precoMax));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
