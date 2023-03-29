package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.MelangeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.MelangeForm;

import java.util.List;

public interface MelangeService {
    List<MelangeDTO> getAll();
    void insert(MelangeForm form);
    void update(Long id, MelangeForm form);
}
