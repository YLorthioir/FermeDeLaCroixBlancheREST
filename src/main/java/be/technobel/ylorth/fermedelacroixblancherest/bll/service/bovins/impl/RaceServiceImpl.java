package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.RaceService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.RaceRepository;
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
    public Set<RaceEntity> getAll() {
        return raceRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public void insert(String nom) {
        if(raceRepository.existsByNom(nom))
            throw new AlreadyExistsException("Race existe déjà");

        RaceEntity entity = new RaceEntity();
        entity.setNom(nom);
        raceRepository.save(entity);
    }

    @Override
    public void update(Long id, String nom) {
        if(raceRepository.existsByNom(nom)&& raceRepository.findByNom(nom).get().getId()!=id)
            throw new AlreadyExistsException("Race déjà existante");

        RaceEntity entity = raceRepository.findById(id).get();
        entity.setNom(nom);
        raceRepository.save(entity);
    }

    @Override
    public RaceEntity getOne(Long id) {
        return raceRepository.findById(id).get();
    }
}
