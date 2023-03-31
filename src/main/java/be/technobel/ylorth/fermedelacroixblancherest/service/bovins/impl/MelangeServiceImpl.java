package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.MelangeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.MelangeForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.MelangeRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.MelangeService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MelangeServiceImpl implements MelangeService {
    private final MelangeRepository melangeRepository;

    public MelangeServiceImpl(MelangeRepository melangeRepository) {
        this.melangeRepository = melangeRepository;
    }

    @Override
    public Set<MelangeDTO> getAll() {
        return melangeRepository.findAll().stream()
                .map(MelangeDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public void insert(MelangeForm form) {

    }

    @Override
    public void update(Long id, MelangeForm form) {

    }

}
