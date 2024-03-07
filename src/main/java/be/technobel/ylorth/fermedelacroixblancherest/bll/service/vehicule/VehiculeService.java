package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Vehicule;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.VehiculeForm;

import java.util.List;
import java.util.Optional;

public interface VehiculeService {
    Optional<Vehicule> getOne(String plaque);
    List<Vehicule> getAll();
    void create(VehiculeForm form);
    void update(VehiculeForm form);
    void delete(String plaque);
}
