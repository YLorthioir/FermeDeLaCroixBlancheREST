package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Vehicule;
import jakarta.validation.constraints.NotBlank;

public record VehiculeDTO(
        @NotBlank
        String plaque,
        String marque,
        String modele
) {
        public static VehiculeDTO fromEntity(Vehicule entity){
                return new VehiculeDTO(entity.getPlaque(), entity.getMarque(), entity.getModele());
        }
}
