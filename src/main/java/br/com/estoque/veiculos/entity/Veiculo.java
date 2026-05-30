package br.com.estoque.veiculos.entity;

import br.com.estoque.veiculos.dto.VeiculoRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String modelo;

    @Column(nullable = false, length = 80)
    private String marca;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 40)
    private String cor;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quilometragem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVeiculo status;

    public Veiculo() {
    }

    public Veiculo(String modelo, String marca, Integer ano, String cor, BigDecimal preco,
                   Integer quilometragem, StatusVeiculo status) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
        this.preco = preco;
        this.quilometragem = quilometragem;
        this.status = status;
    }

    public static Veiculo criar(VeiculoRequestDTO dto) {
        return new Veiculo(
                dto.modelo(),
                dto.marca(),
                dto.ano(),
                dto.cor(),
                dto.preco(),
                dto.quilometragem(),
                dto.status()
        );
    }

    public void atualizar(VeiculoRequestDTO dto) {
        this.modelo = dto.modelo();
        this.marca = dto.marca();
        this.ano = dto.ano();
        this.cor = dto.cor();
        this.preco = dto.preco();
        this.quilometragem = dto.quilometragem();
        this.status = dto.status();
    }

    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }
}
