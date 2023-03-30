package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.RaceDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.BovinService;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.RaceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/bovin")
public class BovinController {

    private final BovinService bovinService;
    private final RaceService raceService;

    public BovinController(BovinService bovinService, RaceService raceService) {
        this.bovinService = bovinService;
        this.raceService = raceService;
    }

    @GetMapping("/all")
    public Set<BovinDTO> getAll(){
        return bovinService.getAll();
    }

    @PostMapping("/add")
    public void addBovin(@RequestBody @Valid BovinInsertForm form){
        bovinService.createBovin(form);
    }

    @GetMapping("/{numeroInscription}")
    public BovinDTO getOne(@PathVariable String numeroInscription){
        return bovinService.getOne(numeroInscription);
    }

    @GetMapping("/race/all")
    public Set<RaceDTO> getAllRace(){
        return raceService.getAll();
    }
}
