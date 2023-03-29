package be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter @Setter
public class RaceDTO {

    private long id;

    private String nom;

    private Set<BovinDTO> bovinsDTO = new LinkedHashSet<>();

}
