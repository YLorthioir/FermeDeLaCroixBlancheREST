package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.FemelleReproduction;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.stream.Collectors;

@SuperBuilder
@Getter @Setter
public class FemelleReproductionDTO extends BovinDTO {

    private LocalDate derniereInsemination;
    private int perteGrossesse;

    public static FemelleReproductionDTO toDTO(FemelleReproduction entity){
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
                .champ(ChampDTO.toDTO(entity.getChamp()))
                .mereNI(entity.getMere() == null? null : entity.getMere().getNumeroInscription())
                .pereNI(entity.getPere() == null? null : entity.getPere().getNumeroInscription())
                .race(RaceDTO.toDTO(entity.getRace()))
                .derniereInsemination(entity.getDerniereInsemination())
                .perteGrossesse(entity.getPerteGrossesse())
                .build();

    }

}
