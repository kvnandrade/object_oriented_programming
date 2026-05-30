package br.com.estoque.veiculos.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho,
        List<String> detalhes
) {
    public static ErroResponse of(int status, String erro, String mensagem, String caminho, List<String> detalhes) {
        return new ErroResponse(LocalDateTime.now(), status, erro, mensagem, caminho, detalhes);
    }
}
