package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.InjectionDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@SuperBuilder
@Getter @Setter
public class BovinDTO {

    private Long id;
    private String numeroInscription;
    private char sexe;
    private LocalDate dateDeNaissance;
    private double poidsNaissance;
    private String nom;
    private boolean enCharge;
    private boolean neCesarienne;
    private Long raceId;
    private Long champId;
    private Set<InjectionDTO> injectionsDTO;
    private Set<ADTO> aDTO;
    private Long pereId;
    private Long mereId;
}
