package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteFaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFaucheForm;

import java.util.List;

public interface VenteFaucheService {

    List<VenteFaucheEntity> getAll();
    VenteFaucheEntity getOne(Long id);
    void insert(VenteFaucheForm form);
    void update(Long id, VenteFaucheForm form);
    void delete(Long id);

}
