package be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule;

import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Intervention;

public record InterventionDTO(
        Long id,
        String description,
        int heuresDeTravail,
        VehiculeDTO vehicule
) {
    public static InterventionDTO fromEntity(Intervention entity){
        return new InterventionDTO(
                entity.getId(), 
                entity.getDescription(),
                entity.getHeuresDeTravail(),
                VehiculeDTO.fromEntity(entity.getVehicule())
        );
    }
}
