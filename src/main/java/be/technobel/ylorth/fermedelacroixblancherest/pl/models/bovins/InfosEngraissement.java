package be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins;


import java.time.LocalDate;

public record InfosEngraissement(
    double poidsSurPattes,
    double poidsCarcasse,
    LocalDate dateEngraissement,
    Melange melange
    ){
}
