package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.MaladieEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Maladie {

    private long id;
    private String nom;

    public static Maladie fromBLL(MaladieEntity entity){

        if(entity==null)
            return null;

        return Maladie.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .build();
    }

}
