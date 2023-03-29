package be.technobel.ylorth.fermedelacroixblancherest.service.sante.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.TraitementDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Traitement;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TraitementMapper {

    public TraitementDTO toDTO (Traitement entity){

        if(entity==null)
            return null;

        return TraitementDTO.builder()
                .id(entity.getId())
                .nomTraitement(entity.getNomTraitement())
                .aId(entity.getA().stream()
                        .map(e->e.getId())
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
