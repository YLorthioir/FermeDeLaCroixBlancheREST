package be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.Bovin;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante.AEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class A {

    private long id;
    private LocalDate anneeMaladie;
    private Bovin bovin;
    private Maladie maladie;
    private Traitement traitement;

    public static A fromBLL(AEntity entity){

        if(entity==null)
            return null;

        return A.builder()
                .id(entity.getId())
                .anneeMaladie(entity.getAnneeMaladie())
                .bovin(Bovin.fromBLL(entity.getBovin()))
                .maladie(Maladie.fromBLL(entity.getMaladie()))
                .traitement(Traitement.fromBLL(entity.getTraitement()))
                .build();
    }
}
