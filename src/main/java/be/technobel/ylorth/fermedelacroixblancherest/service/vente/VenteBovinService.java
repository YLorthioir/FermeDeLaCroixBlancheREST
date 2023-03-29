package be.technobel.ylorth.fermedelacroixblancherest.service.vente;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteBovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.vente.VenteBovinForm;

import java.util.List;

public interface VenteBovinService {
    List<VenteBovinDTO> getAll();
    VenteBovinDTO getOne(Long id);
    void insert(VenteBovinForm form);
    void update(Long id, VenteBovinForm form);
    void delete(Long id);
}
