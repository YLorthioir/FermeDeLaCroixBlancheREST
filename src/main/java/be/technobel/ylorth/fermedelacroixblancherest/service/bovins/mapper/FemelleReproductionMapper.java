package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.FemelleReproductionDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.FemelleReproduction;
import org.springframework.stereotype.Service;

@Service
public class FemelleReproductionMapper {

    public FemelleReproductionDTO toDTO(FemelleReproduction entity){
        if(entity==null)
            return null;

            return FemelleReproductionDTO.builder()
                .id(entity.getId())
                .dateDeNaissance(entity.getDateDeNaissance())
                .enCharge(entity.isEnCharge())
                .neCesarienne(entity.isNeCesarienne())
                .nom(entity.getNom())
                .numeroInscription(entity.getNumeroInscription())
                .poidsNaissance(entity.getPoidsNaissance())
                .sexe(entity.getSexe())
                .champId(entity.getChamp().getId())
                .mereId(entity.getMere().getId())
                .pereId(entity.getPere().getId())
                .raceId(entity.getRace().getId())
                .derniereInsemination(entity.getDerniereInsemination())
                .perteGrossesse(entity.getPerteGrossesse())
                .build();

    }

}
