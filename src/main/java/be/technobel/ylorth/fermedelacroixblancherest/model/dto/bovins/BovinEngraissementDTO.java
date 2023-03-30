package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.BovinEngraissement;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.stream.Collectors;

@SuperBuilder
@Getter @Setter
public class BovinEngraissementDTO extends BovinDTO {

    private double poidsSurPattes;
    private double poidsCarcasse;
    private LocalDate dateEngraissement;
    private Long melangeId;
    private Long venteBovinId;

    public static BovinEngraissementDTO toDTO(BovinEngraissement entity){
        if(entity==null)
            return null;

        return BovinEngraissementDTO.builder()
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
                .dateEngraissement(entity.getDateEngraissement())
                .poidsCarcasse(entity.getPoidsCarcasse())
                .poidsSurPattes(entity.getPoidsSurPattes())
                .melangeId(entity.getMelange().getId())
                .build();

    }

}
