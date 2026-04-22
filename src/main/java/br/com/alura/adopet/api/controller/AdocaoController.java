package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
// classe controller não pode ter regras de negócios, deve apenas controlar o fluxo de requisições.

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    @Transactional

//NÃO É UMA BOA PRÁTICA RECEBER ENTIDADES JPA NA CLASSE CONTROLLER DEVIDO A SEGURANCA, QUANTIDADE DE ATRIBUTOS, ETC.
//DEVE-SE CRIAR UMA NOVA CLASSE SÓ PARA REPRESENTAR APENAS OS DADOS QUE QUEREMOS QUE CHEGUEM NO CONTROLER.
    public ResponseEntity<String> solicitar(@RequestBody @Valid Adocao adocao) {

        try {
            this.adocaoService.solicitar(adocao);
            return ResponseEntity.ok("Adoção solicitada com sucesso");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid Adocao adocao) {
        this.adocaoService.aprovar(adocao);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid Adocao adocao) {

        this.adocaoService.reprovar(adocao);
        return ResponseEntity.ok().build();
    }

}
