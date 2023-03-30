package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@Getter @Setter
public class BovinDTO {

    private Long id;
    private String numeroInscription;
    private char sexe;
    private LocalDate dateDeNaissance;
    private double poidsNaissance;
    private String nom;
    private boolean enCharge;
    private boolean neCesarienne;
    private RaceDTO race;
    private ChampDTO champ;
    private String pereNI;
    private String mereNI;

    public static BovinDTO toDTO(Bovin entity){
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
                .champ(ChampDTO.toDTO(entity.getChamp()))
                .mereNI(entity.getMere() == null? null : entity.getMere().getNumeroInscription())
                .pereNI(entity.getPere() == null? null : entity.getPere().getNumeroInscription())
                .race(RaceDTO.toDTO(entity.getRace()))
                .build();

    }
}
