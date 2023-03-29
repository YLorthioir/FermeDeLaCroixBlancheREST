package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import org.springframework.stereotype.Service;

@Service
public class BovinMapper {


    public BovinDTO toDTO(Bovin entity){
        if(entity==null)
            return null;

        return BovinDTO.builder()
                .id(entity.getId())
                .dateDeNaissance(entity.getDateDeNaissance())
                .enCharge(entity.isEnCharge())
                .neCesarienne(entity.isNeCesarienne())
                .nom(entity.getNom())
                .numeroInscription(entity.getNumeroInscription())
                .poidsNaissance(entity.getPoidsNaissance())
                .sexe(entity.getSexe())
                .champId(0L)
                .mereId(0L)
                .pereId(0L)
                .raceId(0L)
                .build();

    }
}
