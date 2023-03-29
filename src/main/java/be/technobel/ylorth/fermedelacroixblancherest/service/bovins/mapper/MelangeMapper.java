package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.MelangeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Melange;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MelangeMapper {

    private final BovinEngraissementMapper bovinEngraissementMapper;

    public MelangeMapper(BovinEngraissementMapper bovinEngraissementMapper) {
        this.bovinEngraissementMapper = bovinEngraissementMapper;
    }

    public MelangeDTO toDTO(Melange entity) {
        if (entity == null)
            return null;

        return MelangeDTO.builder()
                .id(entity.getId())
                .nomMelange(entity.getNomMelange())
                .description(entity.getDescription())
                .bovinEngraissementsDTO(entity.getBovinEngraissements().stream()
                        .map(bovinEngraissementMapper::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }
}
