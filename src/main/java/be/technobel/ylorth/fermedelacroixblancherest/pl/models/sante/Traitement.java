package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.TraitementEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Traitement {

    private long id;
    private String nomTraitement;
    private boolean actif;

    public static Traitement fromBLL(TraitementEntity entity){

        if(entity==null)
            return null;

        return Traitement.builder()
                .id(entity.getId())
                .nomTraitement(entity.getNomTraitement())
                .actif(entity.isActif())
                .build();
    }

}
