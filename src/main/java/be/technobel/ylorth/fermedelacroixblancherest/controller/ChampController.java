package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.RaceDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.CultureDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.FaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.TypeDeGrainDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.bovins.RaceForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.*;
import be.technobel.ylorth.fermedelacroixblancherest.service.champs.ChampService;
import be.technobel.ylorth.fermedelacroixblancherest.service.champs.FaucheService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public Set<ChampDTO> getAll(){
        return champService.getAll();
    }

    @GetMapping("/one/{id:[0-9]+}")
    public ChampDTO getOne(@PathVariable Long id){return champService.getChamp(id);}

    @PostMapping("/add")
    public void insertChamp(@RequestBody ChampInsertForm form){
        champService.insertChamp(form);
    }

    @PatchMapping("/update/{id:[0-9]+}")
    public void updateChamp(@PathVariable Long id, @RequestBody ChampUpdateForm form){
        champService.updateChamp(id,form);
    }


    //Culture
    @GetMapping("/culture/all/{id:[0-9]+}")
    public Set<CultureDTO> getAllCulture(@PathVariable Long id){
        return champService.getHistorique(id);
    }

    @GetMapping("culture/one/{id:[0-9]+}")
    public CultureDTO getOneCulture(@PathVariable Long id){return champService.getCulture(id);}

    @PostMapping("/culture/add")
    public void insertCulture(@RequestBody CultureForm form){champService.insertCulture(form);}

    @PatchMapping("/culture/update/{id:[0-9]+}")
    public void insertCulture(@PathVariable Long id, @RequestBody CultureForm form){champService.updateCulture(id, form);}

    //Grain
    @GetMapping("/grain/all")
    public Set<TypeDeGrainDTO> getAllGrains(){
        return champService.getAllGrains();
    }

    @PatchMapping("/grain/{id:[0-9]+}")
    public void update(@PathVariable Long id, @RequestBody TypeDeGrainForm form){
        champService.updateGrain(id, form.getNom());
    }

    @PostMapping("/grain/add")
    public void update(@RequestBody String nom){
        champService.insertGrain(nom);
    }

    @GetMapping("/grain/{id:[0-9]+}")
    public TypeDeGrainDTO getOneGrain(@PathVariable Long id){
        return champService.getOneGrain(id);
    }

    //Fauche

    @PostMapping("/fauche/add")
    public void insertFauche(@RequestBody FaucheInsertForm form){faucheService.insert(form);}
    @PatchMapping("/fauche/{id:[0-9]+}")
    public void updateFauche(@PathVariable Long id, @RequestBody FaucheUpdateForm form){faucheService.update(id, form);}
    @GetMapping("/fauche/allChamp/{nomChamp}")
    public Set<FaucheDTO> getAllFaucheChamp(@PathVariable String nomChamp){
        return  faucheService.getAll(nomChamp);
    }
    @GetMapping("/fauche/allAnnee/{annee:[0-9]+}")
    public Set<FaucheDTO> getAllFaucheAnnee(@PathVariable int annee){
        return  faucheService.getAll(annee);
    }
    @GetMapping("/fauche/{id:[0-9]+}")
    public FaucheDTO getOneFauche(@PathVariable Long id){
        return  faucheService.getOne(id);
    }
    @GetMapping("fauche/allAnnee")
    public Set<Integer> getAllAnnee(){return faucheService.getAllAnnee();}

}
