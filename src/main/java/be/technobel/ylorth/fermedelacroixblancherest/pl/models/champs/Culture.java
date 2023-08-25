package be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class Culture {

    private long id;
    private boolean estTemporaire;
    private LocalDate dateMiseEnCulture;
    private LocalDate dateDeFin;
    private String analysePDF;
    private LocalDate dateEpandage;
    private double qttFumier;
    private Champ champ;
    private Long faucheId;
    private TypeDeGrain typeDeGrain;

    public static Culture fromBLL(CultureEntity entity){
        if(entity==null)
            return null;

        return Culture.builder()
                .id(entity.getId())
                .analysePDF(entity.getAnalysePDF())
                .dateDeFin(entity.getDateDeFin())
                .dateEpandage(entity.getDateEpandage())
                .dateMiseEnCulture(entity.getDateMiseEnCulture())
                .estTemporaire(entity.isEstTemporaire())
                .qttFumier(entity.getQttFumier())
                .champ(Champ.fromBLL(entity.getChamp()))
                .typeDeGrain(TypeDeGrain.fromBLL(entity.getTypeDeGrain()))
                .build();
    }

}
