package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.MelangeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Melange;
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
    public MelangeDTO getOne(Long id) {
        return MelangeDTO.toDTO(melangeRepository.findById(id).get());
    }

    @Override
    public void insert(MelangeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");

        if(melangeRepository.existsMelangeByNomMelange(form.getNomMelange()))
            throw new AlreadyExistsException("Mélange existe déjà");

        Melange entity =  new Melange();
        entity.setNomMelange(form.getNomMelange());
        entity.setDescription(form.getDescription());
        melangeRepository.save(entity);
    }

    @Override
    public void update(Long id, MelangeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");

        if(melangeRepository.existsMelangeByNomMelange(form.getNomMelange())&& melangeRepository.findByNomMelange(form.getNomMelange()).get().getId()!=id)
            throw new AlreadyExistsException("Mélange déjà existant");

        Melange entity =  new Melange();
        entity.setNomMelange(form.getNomMelange());
        entity.setDescription(form.getDescription());
        entity.setId(id);
        melangeRepository.save(entity);
    }

}
