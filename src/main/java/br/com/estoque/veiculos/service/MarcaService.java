package br.com.estoque.veiculos.service;

import br.com.estoque.veiculos.dto.MarcaRequestDTO;
import br.com.estoque.veiculos.dto.MarcaResponseDTO;
import br.com.estoque.veiculos.entity.Marca;
import br.com.estoque.veiculos.exception.RecursoNaoEncontradoException;
import br.com.estoque.veiculos.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional
    public MarcaResponseDTO cadastrar(MarcaRequestDTO dto) {
        validarNomeDisponivel(dto.nome(), null);
        Marca marca = marcaRepository.save(Marca.criar(dto));
        return MarcaResponseDTO.fromEntity(marca);
    }

    @Transactional(readOnly = true)
    public List<MarcaResponseDTO> listar() {
        return marcaRepository.findAll()
                .stream()
                .map(MarcaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MarcaResponseDTO buscarPorId(Long id) {
        return MarcaResponseDTO.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public MarcaResponseDTO atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = buscarEntidadePorId(id);
        validarNomeDisponivel(dto.nome(), id);
        marca.atualizar(dto);
        return MarcaResponseDTO.fromEntity(marca);
    }

    @Transactional
    public void excluir(Long id) {
        Marca marca = buscarEntidadePorId(id);
        marcaRepository.delete(marca);
    }

    @Transactional
    public Marca buscarOuCriarPorNome(String nome) {
        String nomeTratado = nome.trim();
        return marcaRepository.findByNomeIgnoreCase(nomeTratado)
                .orElseGet(() -> marcaRepository.save(new Marca(nomeTratado)));
    }

    private Marca buscarEntidadePorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca com id " + id + " nao encontrada"));
    }

    private void validarNomeDisponivel(String nome, Long idAtual) {
        marcaRepository.findByNomeIgnoreCase(nome.trim())
                .filter(marca -> !marca.getId().equals(idAtual))
                .ifPresent(marca -> {
                    throw new IllegalArgumentException("Ja existe uma marca cadastrada com esse nome");
                });
    }
}
