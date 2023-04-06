package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.MelangeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.MelangeForm;

import java.util.Set;

public interface MelangeService {
    Set<MelangeDTO> getAll();
    MelangeDTO getOne(Long id);
    void insert(MelangeForm form);
    void update(Long id, MelangeForm form);
}
