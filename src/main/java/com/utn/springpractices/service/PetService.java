package com.utn.springpractices.service;

import com.utn.springpractices.model.DTO.PetDto;
import com.utn.springpractices.model.Pet;
import com.utn.springpractices.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) { this.petRepository = petRepository; }

    public Pet add(PetDto petDto) {
        return petRepository.save(Pet.buildFromDTO(petDto));
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }
}
