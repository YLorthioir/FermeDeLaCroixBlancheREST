package be.technobel.ylorth.fermedelacroixblancherest.service.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.RaceDTO;

import java.util.List;

public interface RaceService {
    List<RaceDTO> getAll();
    void insert(String nom);
}
