package br.com.guiabolso.challenge.controller;

import br.com.guiabolso.challenge.entity.Transacao;
import br.com.guiabolso.challenge.response.Response;
import br.com.guiabolso.challenge.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/{id}/transacoes/{ano}/{mes}")
    public ResponseEntity<Response<List<Transacao>>> getTransacoesByIdUsuarioAndAnoAndMes(@PathVariable("id") int idUsuario,
                                                                                          @PathVariable("ano") int ano, @PathVariable("mes") int mes) {

        return this.transacaoService.findByIdUsuarioAndAnoAndMes(idUsuario, ano, mes);

    }

}
