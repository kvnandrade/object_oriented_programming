package br.com.estoque.veiculos.controller;

import br.com.estoque.veiculos.dto.VeiculoRequestDTO;
import br.com.estoque.veiculos.dto.VeiculoResponseDTO;
import br.com.estoque.veiculos.entity.StatusVeiculo;
import br.com.estoque.veiculos.service.VeiculoService;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> cadastrar(@RequestBody @Valid VeiculoRequestDTO dto) {
        VeiculoResponseDTO veiculo = veiculoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {
        return ResponseEntity.ok(veiculoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid VeiculoRequestDTO dto) {
        return ResponseEntity.ok(veiculoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        veiculoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<VeiculoResponseDTO>> filtrar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) StatusVeiculo status
    ) {
        return ResponseEntity.ok(veiculoService.filtrar(marca, modelo, ano, precoMin, precoMax, status));
    }
}
