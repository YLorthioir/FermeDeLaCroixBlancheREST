package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.BovinService;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.MelangeService;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.RaceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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

    //Bovins

    @GetMapping("/all")
    public ResponseEntity<Set<String>> getAllNI(){
        return ResponseEntity.ok(bovinService.getAllNI());
    }

    @PostMapping("/add")
    public void addBovin(@RequestBody @Valid BovinInsertForm form){
        bovinService.createBovin(form);
    }

    @GetMapping("/{numeroInscription}")
    public ResponseEntity<Bovin> getOne(@PathVariable String numeroInscription){
        return ResponseEntity.ok(bovinService.getOne(numeroInscription));
    }

    @GetMapping("/enfants/{numeroInscription}")
    public ResponseEntity<Set<Bovin>> getChildren(@PathVariable String numeroInscription){
        return ResponseEntity.ok(bovinService.getChildren(numeroInscription).stream().map(Bovin::fromBLL).collect(Collectors.toSet()));
    }

    @PutMapping("/{id:[0-9]+}")
    public void update(@PathVariable Long id, @RequestBody @Valid BovinUpdateForm form){
        bovinService.updateBovin(id, form);
    }

    @GetMapping("/reproduction/{id:[0-9]+}")
    public ResponseEntity<InfosReproduction> getInfosReproduction(@PathVariable Long id){
        return ResponseEntity.ok(bovinService.getInfosReproduction(id));
    }

    @GetMapping("/engraissement/{id:[0-9]+}")
    public ResponseEntity<InfosEngraissement> getInfosEngraissement(@PathVariable Long id){
        return ResponseEntity.ok(bovinService.getInfosEngraissement(id));
    }

    @PutMapping("/type/{id:[0-9]+}")
    public void updateType(@PathVariable Long id, @RequestBody @Valid BovinUpdateTypeForm form){
        bovinService.updateType(id, form);
    }

    @GetMapping("/taureaux")
    public ResponseEntity<Set<Bovin>> getAllTaureaux(){
        return ResponseEntity.ok(bovinService.getAllTaureaux().stream().map(Bovin::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("/bovinEngraissement")
    public ResponseEntity<Set<String>> getAllBovinsEngraissement(){
        return ResponseEntity.ok(bovinService.getAllEngraissement());
    }

    //Race

    @GetMapping("/race/all")
    public ResponseEntity<Set<Race>> getAllRace(){
        return ResponseEntity.ok(raceService.getAll().stream().map(Race::fromBLL).collect(Collectors.toSet()));
    }

    @PatchMapping("/race/{id:[0-9]+}")
    public void update(@PathVariable Long id, @RequestBody @Valid RaceForm form){
        raceService.update(id, form.getNom());
    }

    @PostMapping("/race/add")
    public void update(@RequestBody String nom){
        raceService.insert(nom);
    }

    @GetMapping("/race/{id:[0-9]+}")
    public ResponseEntity<Race> getOne(@PathVariable Long id){
        return ResponseEntity.ok(Race.fromBLL(raceService.getOne(id)));
    }


    //Melange

    @GetMapping("/melange/all")
    public ResponseEntity<Set<Melange>> getAllMelange(){
        return ResponseEntity.ok(melangeService.getAll().stream().map(Melange::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("/melange/{id:[0-9]+}")
    public ResponseEntity<Melange> getMelange(@PathVariable Long id){
        return ResponseEntity.ok(Melange.fromBLL(melangeService.getOne(id)));
    }

    @PostMapping("/melange/add")
    public void insertMelange(@RequestBody @Valid MelangeForm form){
        melangeService.insert(form);
    }

    @PatchMapping("/melange/{id:[0-9]+}")
    public void updateMelange(@PathVariable Long id, @RequestBody @Valid MelangeForm form){
        melangeService.update(id, form);
    }
}
