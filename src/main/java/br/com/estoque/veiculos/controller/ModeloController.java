package br.com.estoque.veiculos.controller;

import br.com.estoque.veiculos.dto.ModeloRequestDTO;
import br.com.estoque.veiculos.dto.ModeloResponseDTO;
import br.com.estoque.veiculos.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping
    public ResponseEntity<ModeloResponseDTO> cadastrar(@RequestBody @Valid ModeloRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ModeloResponseDTO>> listar(@RequestParam(required = false) Long marcaId) {
        if (marcaId != null) {
            return ResponseEntity.ok(modeloService.listarPorMarca(marcaId));
        }

        return ResponseEntity.ok(modeloService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid ModeloRequestDTO dto) {
        return ResponseEntity.ok(modeloService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        modeloService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
