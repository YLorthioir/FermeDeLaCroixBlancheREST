package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.MelangeForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.MelangeRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.MelangeService;
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
    public Set<MelangeEntity> getAll() {
        return melangeRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public MelangeEntity getOne(Long id) {
        return melangeRepository.findById(id).get();
    }

    @Override
    public void insert(MelangeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");

        if(melangeRepository.existsMelangeByNomMelange(form.getNomMelange()))
            throw new AlreadyExistsException("Mélange existe déjà");

        MelangeEntity entity =  new MelangeEntity();
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

        MelangeEntity entity =  new MelangeEntity();
        entity.setNomMelange(form.getNomMelange());
        entity.setDescription(form.getDescription());
        entity.setId(id);
        melangeRepository.save(entity);
    }

}
