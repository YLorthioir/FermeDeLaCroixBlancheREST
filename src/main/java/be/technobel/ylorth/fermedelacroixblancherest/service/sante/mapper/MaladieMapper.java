package be.technobel.ylorth.fermedelacroixblancherest.service.sante.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.MaladieDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Maladie;
import org.springframework.stereotype.Service;

@Service
public class MaladieMapper {

    public MaladieDTO toDTO (Maladie entity){

        if(entity==null)
            return null;

        return MaladieDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .build();
    }
}
