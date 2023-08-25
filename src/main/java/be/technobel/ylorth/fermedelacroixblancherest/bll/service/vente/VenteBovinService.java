package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteBovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovinForm;

import java.util.List;

public interface VenteBovinService {
    List<VenteBovinEntity> getAll();
    VenteBovinEntity getOne(Long id);
    void insert(VenteBovinForm form);
    void update(Long id, VenteBovinForm form);
    void delete(Long id);
}
