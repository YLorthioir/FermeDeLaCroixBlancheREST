package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Intervention;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.InterventionForm;

import java.util.List;

public interface InterventionService {
    List<Intervention> getAll(String plaque);
    void save(InterventionForm form);
    void delete(Long id);
    void update(Long id, InterventionForm form);
}
