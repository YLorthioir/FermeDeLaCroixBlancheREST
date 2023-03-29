package be.technobel.ylorth.fermedelacroixblancherest.service.vente;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteFaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.vente.VenteFaucheForm;

import java.util.List;

public interface VenteFaucheService {

    List<VenteFaucheDTO> getAll();
    VenteFaucheDTO getOne(Long id);
    void insert(VenteFaucheForm form);
    void update(Long id, VenteFaucheForm form);
    void delete(Long id);

}
