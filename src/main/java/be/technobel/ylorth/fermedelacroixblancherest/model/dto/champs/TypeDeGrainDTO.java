package be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter @Setter
public class TypeDeGrainDTO {

    private long id;
    private String nomGrain;
    private Set<Long> culturesId = new LinkedHashSet<>();

}
