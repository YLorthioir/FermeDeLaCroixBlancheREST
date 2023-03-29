package be.technobel.ylorth.fermedelacroixblancherest.service.champs.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.FaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Fauche;
import org.springframework.stereotype.Service;

@Service
public class FaucheMapper {

    private final CultureMapper cultureMapper;

    public FaucheMapper(CultureMapper cultureMapper) {
        this.cultureMapper = cultureMapper;
    }

    public FaucheDTO toDTO(Fauche entity){

        if(entity==null)
            return null;

        return FaucheDTO.builder()
                .id(entity.getId())
                .annee(entity.getAnnee())
                .cultureDTO(cultureMapper.toDTO(entity.getCulture()))
                .fauche1(entity.getFauche1())
                .fauche2(entity.getFauche2())
                .fauche3(entity.getFauche3())
                .fauche4(entity.getFauche4())
                .fauche1rendement(entity.getFauche1rendement())
                .fauche2rendement(entity.getFauche2rendement())
                .fauche3rendement(entity.getFauche3rendement())
                .fauche4rendement(entity.getFauche4rendement())
                .build();
    }
}
