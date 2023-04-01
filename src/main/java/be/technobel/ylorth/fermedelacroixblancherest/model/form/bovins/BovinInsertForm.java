package be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BovinInsertForm {
    @NotBlank
    private String numeroInscription;
    private String sexe;
    @Past
    private LocalDate dateDeNaissance;
    @Positive
    private double poidsNaissance;
    private boolean neCesarienne;
    private Long raceId;
    private String pereNI;
    private String mereNI;

    public static Bovin toEntity(BovinInsertForm form){
        Bovin entity = new Bovin();
        entity.setNumeroInscription(form.numeroInscription);
        entity.setSexe(form.sexe.charAt(0));
        entity.setDateDeNaissance(form.dateDeNaissance);
        entity.setPoidsNaissance(form.poidsNaissance);
        entity.setNeCesarienne(form.neCesarienne);
        entity.setPereNI(form.pereNI);
        entity.setMereNI(form.mereNI);
        entity.setEnCharge(true);
        return entity;
    }

}
