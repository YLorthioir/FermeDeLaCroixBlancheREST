package be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante;

import be.technobel.ylorth.fermedelacroixblancherest.bll.models.Vaccination;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.MaladieEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.TraitementEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.VaccinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.AForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.VaccinForm;

import java.util.List;
import java.util.Set;

public interface SanteService {
    void insertInjection(Long idBovin, String nom);
    Set<Vaccination> getCarnetVaccination(Long idBovin);
    VaccinEntity getVaccin(String nom);
    void insertVaccin(VaccinForm form);
    void updateVaccin(Long id, VaccinForm form);
    Set<VaccinEntity> getAllVaccin();
    Set<MaladieEntity> getAllMaladie();
    void insertMaladie(String nom);
    MaladieEntity getMaladie(Long id);
    void updateMaladie(Long id, String nom);
    Set<AEntity> getAllA(Long idBovin);
    AEntity getOneA(Long aId);
    void insertA(Long idBovin, AForm form);
    void updateA(Long id, AForm form);
    void deleteA(Long id);
    void insertTraitement(TraitementForm form);
    void updateTraitement(Long id, TraitementForm form);
    Set<TraitementEntity> getAllTraitement();
    TraitementEntity getTraitement(Long id);
    List<String> toVaccinate(Long id);
}
