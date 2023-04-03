package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
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
    private int nbCesarienne;

    public static BovinDTO toDTO(Bovin entity){
        if(entity==null)
            return null;

        return BovinDTO.builder()
                .id(entity.getId())
                .numeroInscription(entity.getNumeroInscription())
                .dateDeNaissance(entity.getDateDeNaissance())
                .enCharge(entity.isEnCharge())
                .neCesarienne(entity.isNeCesarienne())
                .nom(entity.getNom())
                .poidsNaissance(entity.getPoidsNaissance())
                .sexe(entity.getSexe())
                .champ(ChampDTO.toDTO(entity.getChamp()))
                .mereNI(entity.getMereNI())
                .pereNI(entity.getPereNI())
                .race(RaceDTO.toDTO(entity.getRace()))
                .build();

    }
}
