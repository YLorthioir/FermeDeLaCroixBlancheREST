package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter @Setter
public class ChampDTO {

    private long id;
    private String lieu;
    private double superficie;
    private LocalDate dateDerniereChaux;

    private Set<BovinDTO> bovins = new LinkedHashSet<>();


    private Set<CultureDTO> culturesDTO = new LinkedHashSet<>();

}
