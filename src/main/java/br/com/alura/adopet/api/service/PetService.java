package br.com.alura.adopet.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;

import br.com.alura.adopet.api.repository.PetRepository;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<PetDTO> listarTodosPetsDisponiveis(){
          return petRepository.findAllByAdotadoFalse().stream().map(PetDTO::new).toList();
    

    }

    public List<Pet> listarTodosPetsDisponiveisPorAbrigo(Abrigo abrigo){
          return petRepository.findByAbrigo(abrigo);
    

    }

    public void cadastrarPet(Abrigo abrigo, CadastroPetDTO dto) {

        petRepository.save(new Pet(dto, abrigo));

    }

}
