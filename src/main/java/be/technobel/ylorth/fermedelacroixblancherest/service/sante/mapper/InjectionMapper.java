package be.technobel.ylorth.fermedelacroixblancherest.service.sante.mapper;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.InjectionDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante.Injection;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.mapper.BovinMapper;
import org.springframework.stereotype.Service;

@Service
public class InjectionMapper {

    private final BovinMapper bovinMapper;
    private final VaccinMapper vaccinMapper;

    public InjectionMapper(BovinMapper bovinMapper, VaccinMapper vaccinMapper) {
        this.bovinMapper = bovinMapper;
        this.vaccinMapper = vaccinMapper;
    }

    public InjectionDTO toDTO (Injection entity){

        if(entity==null)
            return null;

        return InjectionDTO.builder()
                .id(entity.getId())
                .dateInjection(entity.getDateInjection())
                .bovinDTO(bovinMapper.toDTO(entity.getBovin()))
                .vaccinDTO(vaccinMapper.toDTO(entity.getVaccin()))
                .build();
    }
}
