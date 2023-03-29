package be.technobel.ylorth.fermedelacroixblancherest.service.champs.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Champ;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper.BovinMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ChampMapper {

    private BovinMapper bovinMapper;
    private CultureMapper cultureMapper;

    public ChampMapper(BovinMapper bovinMapper, CultureMapper cultureMapper) {
        this.bovinMapper = bovinMapper;
        this.cultureMapper = cultureMapper;
    }

    public ChampDTO toDTO (Champ entity){
        if (entity==null)
            return null;

        return ChampDTO.builder()
                .id(entity.getId())
                .dateDerniereChaux(entity.getDateDerniereChaux())
                .lieu(entity.getLieu())
                .superficie(entity.getSuperficie())
                .bovins(entity.getBovins().stream()
                        .map(bovinMapper::toDTO)
                        .collect(Collectors.toSet()))
                .culturesDTO(entity.getCultures().stream()
                        .map(cultureMapper::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }
}
