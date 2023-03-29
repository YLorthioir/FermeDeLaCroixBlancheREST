package be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class InjectionDTO {

    private long id;
    private LocalDate dateInjection;
    private BovinDTO bovinDTO;
    private VaccinDTO vaccinDTO;

}
