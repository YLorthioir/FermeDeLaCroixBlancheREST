package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.RaceDTO;

import java.util.Set;

public interface RaceService {
    Set<RaceDTO> getAll();
    void insert(String nom);
}
