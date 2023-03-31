package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.service.champs.ChampService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/champ")
public class ChampController {
    private final ChampService champService;

    public ChampController(ChampService champService) {
        this.champService = champService;
    }

    @GetMapping("/all")
    public Set<ChampDTO> getAll(){
        return champService.getAll();
    }
}
