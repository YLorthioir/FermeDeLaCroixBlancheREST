package be.technobel.ylorth.fermedelacroixblancherest.service.champs.impl;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.CultureDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.CultureUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.champs.ChampService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChampServiceImpl implements ChampService {

    private final ChampRepository champRepository;

    public ChampServiceImpl(ChampRepository champRepository) {
        this.champRepository = champRepository;
    }

    @Override
    public Set<ChampDTO> getAll() {
        return champRepository.findAll().stream()
                .map(ChampDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ChampDTO getChamp(Long id) {
        return null;
    }

    @Override
    public CultureDTO getCulture(Long id) {
        return null;
    }

    @Override
    public void insert(ChampInsertForm form) {

    }

    @Override
    public void updateChamp(Long id, ChampUpdateForm form) {

    }

    @Override
    public void updateCulture(Long id, CultureUpdateForm form) {

    }

    @Override
    public void getHistorique(Long id) {

    }

    @Override
    public void insertGrain(String nom) {

    }
}
