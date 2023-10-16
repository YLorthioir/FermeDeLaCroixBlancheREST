package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.*;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.ChampService;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.FaucheService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/champ")
public class ChampController {
    private final ChampService champService;
    private final FaucheService faucheService;

    public ChampController(ChampService champService, FaucheService faucheService) {
        this.champService = champService;
        this.faucheService = faucheService;
    }

    //Champs

    @GetMapping("/all")
    public ResponseEntity<Set<Champ>> getAll(){
        return ResponseEntity.ok(champService.getAll().stream().map(Champ::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("/one/{id:[0-9]+}")
    public ResponseEntity<Champ> getOne(@PathVariable Long id){return ResponseEntity.ok(Champ.fromBLL(champService.getChamp(id)));}

    @PostMapping("/add")
    public void insertChamp(@RequestBody @Valid ChampInsertForm form){
        champService.insertChamp(form);
    }

    @PatchMapping("/update/{id:[0-9]+}")
    public void updateChamp(@PathVariable Long id, @RequestBody @Valid ChampUpdateForm form){
        champService.updateChamp(id,form);
    }


    //Culture
    @GetMapping("/culture/all/{id:[0-9]+}")
    public ResponseEntity<Set<Culture>> getAllCulture(@PathVariable Long id){
        return ResponseEntity.ok(champService.getHistorique(id).stream().map(Culture::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("culture/one/{id:[0-9]+}")
    public ResponseEntity<Culture> getOneCulture(@PathVariable Long id){return ResponseEntity.ok(Culture.fromBLL(champService.getCulture(id)));}

    @PostMapping("/culture/add")
    public void insertCulture(@RequestBody @Valid CultureForm form){champService.insertCulture(form);}

    @PatchMapping("/culture/update/{id:[0-9]+}")
    public void insertCulture(@PathVariable Long id, @RequestBody @Valid CultureForm form){champService.updateCulture(id, form);}

    //Grain
    @GetMapping("/grain/all")
    public ResponseEntity<Set<TypeDeGrain>> getAllGrains(){
        return ResponseEntity.ok(champService.getAllGrains().stream().map(TypeDeGrain::fromBLL).collect(Collectors.toSet()));
    }

    @PatchMapping("/grain/{id:[0-9]+}")
    public void update(@PathVariable Long id, @RequestBody @Valid TypeDeGrainForm form){
        champService.updateGrain(id, form.nomGrain());
    }

    @PostMapping("/grain/add")
    public void insertGrains(@RequestBody @Valid String nom){
        System.out.println(nom);
        champService.insertGrain(nom);
    }

    @GetMapping("/grain/{id:[0-9]+}")
    public ResponseEntity<TypeDeGrain> getOneGrain(@PathVariable Long id){
        return ResponseEntity.ok(TypeDeGrain.fromBLL(champService.getOneGrain(id)));
    }

    //Fauche

    @PostMapping("/fauche/add")
    public void insertFauche(@RequestBody @Valid FaucheInsertForm form){faucheService.insert(form);}
    @PatchMapping("/fauche/{id:[0-9]+}")
    public void updateFauche(@PathVariable Long id, @RequestBody @Valid FaucheUpdateForm form){faucheService.update(id, form);}
    @GetMapping("/fauche/allChamp/{nomChamp}")
    public ResponseEntity<Set<Fauche>> getAllFaucheChamp(@PathVariable String nomChamp){
        return  ResponseEntity.ok(faucheService.getAll(nomChamp).stream().map(Fauche::fromBLL).collect(Collectors.toSet()));
    }
    @GetMapping("/fauche/allAnnee/{annee:[0-9]+}")
    public ResponseEntity<Set<Fauche>> getAllFaucheAnnee(@PathVariable int annee){
        return  ResponseEntity.ok(faucheService.getAll(annee).stream().map(Fauche::fromBLL).collect(Collectors.toSet()));
    }
    @GetMapping("/fauche/{id:[0-9]+}")
    public ResponseEntity<Fauche> getOneFauche(@PathVariable Long id){
        return  ResponseEntity.ok(Fauche.fromBLL(faucheService.getOne(id)));
    }
    @GetMapping("fauche/allAnnee")
    public ResponseEntity<Set<Integer>> getAllAnnee(){return ResponseEntity.ok(faucheService.getAllAnnee());}

}
