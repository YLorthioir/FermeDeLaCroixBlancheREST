package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.RaceDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Race;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.RaceRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.RaceService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;

    public RaceServiceImpl(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Set<RaceDTO> getAll() {
        return raceRepository.findAll().stream()
                .map(RaceDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public void insert(String nom) {
        if(raceRepository.existsByNom(nom))
            throw new AlreadyExistsException("Race existe déjà");

        Race entity = new Race();
        entity.setNom(nom);
        raceRepository.save(entity);
    }

    @Override
    public void update(Long id, String nom) {
        if(!raceRepository.existsByNom(nom)){
            Race entity = raceRepository.findById(id).get();
            entity.setNom(nom);
            raceRepository.save(entity);
        }
    }

    @Override
    public RaceDTO getOne(Long id) {
        return RaceDTO.toDTO(raceRepository.findById(id).get());
    }
}
