package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.TraitementDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementForm;
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
    public Set<String> getAllNI(){
        return bovinService.getAllNI();
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

    @PatchMapping("/race/{id:[0-9]+}")
    public void update(@PathVariable Long id, @RequestBody RaceForm form){
        raceService.update(id, form.getNom());
    }

    @PostMapping("/race/add")
    public void update(@RequestBody String nom){
        raceService.insert(nom);
    }

    @GetMapping("/race/{id:[0-9]+}")
    public RaceDTO getOne(@PathVariable Long id){
        return raceService.getOne(id);
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

    @PutMapping("type/{id:[0-9]+}")
    public void updateType(@PathVariable Long id, @RequestBody @Valid BovinUpdateTypeForm form){
        bovinService.updateType(id, form);
    }

    //Melange

    @GetMapping("/melange/all")
    public Set<MelangeDTO> getAllMelange(){
        return melangeService.getAll();
    }

    @GetMapping("/melange/{id:[0-9]+}")
    public MelangeDTO getMelange(@PathVariable Long id){
        return melangeService.getOne(id);
    }

    @PostMapping("/melange/add")
    public void insertMelange(@RequestBody MelangeForm form){
        melangeService.insert(form);
    }

    @PatchMapping("/melange/{id:[0-9]+}")
    public void updateMelange(@PathVariable Long id, @RequestBody MelangeForm form){
        melangeService.update(id, form);
    }
}
