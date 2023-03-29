package be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.RaceDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Race;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RaceMapper {

    private final BovinMapper bovinMapper;

    public RaceMapper(BovinMapper bovinMapper) {
        this.bovinMapper = bovinMapper;
    }

    public RaceDTO toDTO(Race entity) {
        if (entity == null)
            return null;

        return RaceDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .bovinsDTO(entity.getBovins().stream()
                        .map(bovinMapper::toDTO)
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
