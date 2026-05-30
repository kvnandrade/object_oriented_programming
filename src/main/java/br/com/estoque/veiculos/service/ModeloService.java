package br.com.estoque.veiculos.service;

import br.com.estoque.veiculos.dto.ModeloRequestDTO;
import br.com.estoque.veiculos.dto.ModeloResponseDTO;
import br.com.estoque.veiculos.entity.Marca;
import br.com.estoque.veiculos.entity.Modelo;
import br.com.estoque.veiculos.exception.RecursoNaoEncontradoException;
import br.com.estoque.veiculos.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaService marcaService;

    public ModeloService(ModeloRepository modeloRepository, MarcaService marcaService) {
        this.modeloRepository = modeloRepository;
        this.marcaService = marcaService;
    }

    @Transactional
    public ModeloResponseDTO cadastrar(ModeloRequestDTO dto) {
        validarModeloDisponivel(dto.nome(), dto.marca(), null);
        Marca marca = marcaService.buscarOuCriarPorNome(dto.marca());
        Modelo modelo = modeloRepository.save(Modelo.criar(dto, marca));
        return ModeloResponseDTO.fromEntity(modelo);
    }

    @Transactional(readOnly = true)
    public List<ModeloResponseDTO> listar() {
        return modeloRepository.findAll()
                .stream()
                .map(ModeloResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ModeloResponseDTO buscarPorId(Long id) {
        return ModeloResponseDTO.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public List<ModeloResponseDTO> listarPorMarca(Long marcaId) {
        return modeloRepository.findByMarcaId(marcaId)
                .stream()
                .map(ModeloResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ModeloResponseDTO atualizar(Long id, ModeloRequestDTO dto) {
        Modelo modelo = buscarEntidadePorId(id);
        validarModeloDisponivel(dto.nome(), dto.marca(), id);
        Marca marca = marcaService.buscarOuCriarPorNome(dto.marca());
        modelo.atualizar(dto, marca);
        return ModeloResponseDTO.fromEntity(modelo);
    }

    @Transactional
    public void excluir(Long id) {
        Modelo modelo = buscarEntidadePorId(id);
        modeloRepository.delete(modelo);
    }

    @Transactional
    public Modelo buscarOuCriarPorNomeEMarca(String nomeModelo, String nomeMarca) {
        String modeloTratado = nomeModelo.trim();
        String marcaTratada = nomeMarca.trim();

        return modeloRepository.findByNomeIgnoreCaseAndMarca_NomeIgnoreCase(modeloTratado, marcaTratada)
                .orElseGet(() -> {
                    Marca marca = marcaService.buscarOuCriarPorNome(marcaTratada);
                    return modeloRepository.save(new Modelo(modeloTratado, marca));
                });
    }

    private Modelo buscarEntidadePorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo com id " + id + " nao encontrado"));
    }

    private void validarModeloDisponivel(String nomeModelo, String nomeMarca, Long idAtual) {
        modeloRepository.findByNomeIgnoreCaseAndMarca_NomeIgnoreCase(nomeModelo.trim(), nomeMarca.trim())
                .filter(modelo -> !modelo.getId().equals(idAtual))
                .ifPresent(modelo -> {
                    throw new IllegalArgumentException("Ja existe esse modelo cadastrado para a marca informada");
                });
    }
}
