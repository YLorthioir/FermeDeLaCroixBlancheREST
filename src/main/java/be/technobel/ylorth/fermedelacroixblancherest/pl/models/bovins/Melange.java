package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;


import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;

public record Melange (
        long id,
        String nomMelange,
        String description
        ){

    public static Melange fromBLL(MelangeEntity entity) {
        if (entity == null)
            return null;

        return new Melange(
                entity.getId(),
                entity.getNomMelange(),
                entity.getDescription()
                );
    }

}
