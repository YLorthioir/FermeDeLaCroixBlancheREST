package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.BovinUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.BovinService;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.MelangeService;
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
    private final MelangeService melangeService;

    public BovinController(BovinService bovinService, RaceService raceService, MelangeService melangeService) {
        this.bovinService = bovinService;
        this.raceService = raceService;
        this.melangeService = melangeService;
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

    @GetMapping("/melange/all")
    public Set<MelangeDTO> getAllMelange(){
        return melangeService.getAll();
    }


    @GetMapping("/enfants/{numeroInscription}")
    public Set<BovinDTO> getChildren(@PathVariable String numeroInscription){
        return bovinService.getChildren(numeroInscription);
    }

    @PutMapping("/{id:[0-9]+}")
    public void update(@PathVariable Long id, @RequestBody @Valid BovinUpdateForm form){
        bovinService.updateBovin(id, form);
    }

    @GetMapping("reproduction/{id:[0-9]+}")
    public InfosReproduction getInfosReproduction(@PathVariable Long id){
        return bovinService.getInfosReproduction(id);
    }

    @GetMapping("engraissement/{id:[0-9]+}")
    public InfosEngraissement getInfosEngraissement(@PathVariable Long id){
        return bovinService.getInfosEngraissement(id);
    }
}
