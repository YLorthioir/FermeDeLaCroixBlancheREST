package be.technobel.ylorth.fermedelacroixblancherest.service.sante.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.VaccinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Vaccin;
import org.springframework.stereotype.Service;

@Service
public class VaccinMapper {

    public VaccinDTO toDTO(Vaccin entity){

        if(entity==null)
            return null;

        return VaccinDTO.builder()
                .id(entity.getId())
                .actif(entity.isActif())
                .delai(entity.getDelai())
                .dosage(entity.getDosage())
                .nbDose(entity.getNbDose())
                .nom(entity.getNom())
                .build();

    }
}
