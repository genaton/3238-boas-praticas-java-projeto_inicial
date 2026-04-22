package br.com.alura.adopet.api.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;

@Service
public class AdocaoService {

    public void solicitar(){
if (adocao.getPet().getAdotado() == true) {
            return ResponseEntity.badRequest().body("Pet já foi adotado!");
        } else {
            List<Adocao> adocoes = repository.findAll();
            for (Adocao a : adocoes) {
                if (a.getTutor() == adocao.getTutor() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    return ResponseEntity.badRequest().body("Tutor já possui outra adoção aguardando avaliação!");
                }
            }
            for (Adocao a : adocoes) {
                if (a.getPet() == adocao.getPet() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    return ResponseEntity.badRequest().body("Pet já está aguardando avaliação para ser adotado!");
                }
            }
            for (Adocao a : adocoes) {
                int contador = 0;
                if (a.getTutor() == adocao.getTutor() && a.getStatus() == StatusAdocao.APROVADO) {
                    contador = contador + 1;
                }
                if (contador == 5) {
                    return ResponseEntity.badRequest().body("Tutor chegou ao limite máximo de 5 adoções!");
                }
            }
        }
        adocao.setData(LocalDateTime.now());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        repository.save(adocao);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adocao.getPet().getAbrigo().getEmail());
        email.setSubject("Solicitação de adoção");
        email.setText("Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.");
        emailSender.send(email);

        return ResponseEntity.ok().build();
        

    }

    public void aprovar(){

    }

    public void reprovar(){

    }

}
