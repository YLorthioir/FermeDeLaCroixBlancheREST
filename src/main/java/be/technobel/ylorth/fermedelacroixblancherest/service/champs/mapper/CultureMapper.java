package be.technobel.ylorth.fermedelacroixblancherest.service.champs.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.CultureDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Culture;
import org.springframework.stereotype.Service;

@Service
public class CultureMapper {

    private GrainMapper grainMapper;

    public CultureMapper(GrainMapper grainMapper) {
        this.grainMapper = grainMapper;
    }

    public CultureDTO toDTO (Culture entity){
        if(entity==null)
            return null;

        return CultureDTO.builder()
                .analysePDF(entity.getAnalysePDF())
                .dateDeFin(entity.getDateDeFin())
                .dateEpandage(entity.getDateEpandage())
                .dateMiseEnCulture(entity.getDateMiseEnCulture())
                .estTemporaire(entity.isEstTemporaire())
                .qttFumier(entity.getQttFumier())
                .champId(entity.getChamp().getId())
                .typeDeGrainDTO(grainMapper.toDTO(entity.getTypeDeGrain()))
                .build();
    }

}
