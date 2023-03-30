package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinEngraissementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinReproductionUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.BovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.RaceRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.BovinService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BovinServiceImpl implements BovinService {

    private final BovinRepository<Bovin, Number> bovinRepository;
    private final RaceRepository raceRepository;

    public BovinServiceImpl(BovinRepository<Bovin, Number> bovinRepository,
                            RaceRepository raceRepository) {
        this.bovinRepository = bovinRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public Set<BovinDTO> getAll(){
        return  bovinRepository.findAll().stream()
                .map(BovinDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public BovinDTO getOne(String numeroInscription) {
        return bovinRepository.findByNumeroInscription(numeroInscription)
                .map(BovinDTO::toDTO)
                .orElseThrow(() -> new NotFoundException("bovin not found"));
    }

    @Override
    public Set<BovinDTO> getChildren(Long id) {
        return null;
    }

    @Override
    public Set<BovinDTO> getParents(Long id) {
        return null;
    }

    @Override
    public void createBovin(BovinInsertForm form) {
        if(form == null)
            throw new IllegalArgumentException("le formulaire ne peut être null");

        Bovin entity = BovinInsertForm.toEntity(form);
        entity.setPere(bovinRepository.findByNumeroInscription(form.getPereNI())
                .orElseThrow(() -> new NotFoundException("bovin not found")));
        entity.setMere(bovinRepository.findByNumeroInscription(form.getMereNI())
                .orElseThrow(() -> new NotFoundException("bovin not found")));
        entity.setRace(raceRepository.findById(form.getRaceId())
                .orElseThrow(() -> new NotFoundException("race not found")));

        entity = bovinRepository.save(entity);
    }

    @Override
    public void updateBovinEngraissement(Long id, BovinEngraissementUpdateForm form) {

    }

    @Override
    public void updateBovinReproduction(Long id, BovinReproductionUpdateForm form) {

    }
}
