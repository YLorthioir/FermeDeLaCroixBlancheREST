package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinEngraissementUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinReproductionUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.BovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.BovinService;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper.BovinMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BovinServiceImpl implements BovinService {

    private final BovinRepository bovinRepository;
    private final BovinMapper bovinMapper;

    public BovinServiceImpl(BovinRepository bovinRepository, BovinMapper bovinMapper) {
        this.bovinRepository = bovinRepository;
        this.bovinMapper = bovinMapper;
    }

    @Override
    public Set<BovinDTO> getAll(){
        return  bovinRepository.findAll().stream()
                .map(bovinMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public BovinDTO getOne(String numeroInscription) {
        return bovinRepository.findByNumeroInscription(numeroInscription)
                .map(bovinMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("material not found"));
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

    }

    @Override
    public void updateBovinEngraissement(Long id, BovinEngraissementUpdateForm form) {

    }

    @Override
    public void updateBovinReproduction(Long id, BovinReproductionUpdateForm form) {

    }
}
