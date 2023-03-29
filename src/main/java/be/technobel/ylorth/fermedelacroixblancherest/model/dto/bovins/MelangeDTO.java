package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter @Setter
public class MelangeDTO {

    private long id;

    private String nomMelange;
    private String description;

    private Set<BovinEngraissementDTO> bovinEngraissementsDTO = new LinkedHashSet<>();

}
