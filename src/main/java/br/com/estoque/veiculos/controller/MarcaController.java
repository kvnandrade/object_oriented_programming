package br.com.estoque.veiculos.controller;

import br.com.estoque.veiculos.dto.MarcaRequestDTO;
import br.com.estoque.veiculos.dto.MarcaResponseDTO;
import br.com.estoque.veiculos.service.MarcaService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDTO> cadastrar(@RequestBody @Valid MarcaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> listar() {
        return ResponseEntity.ok(marcaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> atualizar(@PathVariable Long id,
                                                      @RequestBody @Valid MarcaRequestDTO dto) {
        return ResponseEntity.ok(marcaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        marcaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
