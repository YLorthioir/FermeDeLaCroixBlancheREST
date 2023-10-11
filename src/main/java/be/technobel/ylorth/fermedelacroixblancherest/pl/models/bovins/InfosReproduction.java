package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;

import java.time.LocalDate;

public record InfosReproduction (
        LocalDate derniereInsemination,
        int perteGrossesse,
        int nbCesarienne
        ){

}
