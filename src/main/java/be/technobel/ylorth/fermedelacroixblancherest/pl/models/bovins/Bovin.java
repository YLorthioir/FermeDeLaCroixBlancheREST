package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.Champ;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.BovinEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class Bovin {

    private Long id;
    private String numeroInscription;
    private char sexe;
    private LocalDate dateDeNaissance;
    private double poidsNaissance;
    private String nom;
    private boolean enCharge;
    private boolean neCesarienne;
    private Race race;
    private Champ champ;
    private String pereNI;
    private String mereNI;
    private int nbCesarienne;
    private LocalDate dateAbattage;
    private String raisonAbattage;

    public static Bovin fromBLL(BovinEntity entity){
        if(entity==null)
            return null;

        return Bovin.builder()
                .id(entity.getId())
                .numeroInscription(entity.getNumeroInscription())
                .dateDeNaissance(entity.getDateDeNaissance())
                .enCharge(entity.isEnCharge())
                .neCesarienne(entity.isNeCesarienne())
                .nom(entity.getNom())
                .poidsNaissance(entity.getPoidsNaissance())
                .sexe(entity.getSexe())
                .champ(Champ.fromBLL(entity.getChamp()))
                .mereNI(entity.getMereNI())
                .pereNI(entity.getPereNI())
                .race(Race.fromBLL(entity.getRace()))
                .raisonAbattage(entity.getRaisonAbattage())
                .dateAbattage(entity.getDateAbattage())
                .build();

    }
}
