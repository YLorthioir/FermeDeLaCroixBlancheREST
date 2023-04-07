package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.MaladieForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.sante.VaccinForm;
import be.technobel.ylorth.fermedelacroixblancherest.service.sante.SanteService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/sante")
public class SanteController {

    private final SanteService santeService;

    public SanteController(SanteService santeService) {
        this.santeService = santeService;
    }

    //Vaccin

    @GetMapping("/vaccination/{id:[0-9]+}")
    public Set<Vaccination> getCarnetVaccination(@PathVariable Long id){
        return santeService.getCarnetVaccination(id);
    }
    @PostMapping("/vaccination/{id:[0-9]+}")
    public void vacciante(@PathVariable Long id, @RequestBody String nom){
        santeService.insertInjection(id,nom);
    }
    @GetMapping("/vaccin/{nom}")
    public VaccinDTO getVaccin(@PathVariable String nom){
        return santeService.getVaccin(nom);
    }
    @PostMapping("vaccin/add")
    public void insertVaccin(@RequestBody VaccinForm form){
        santeService.insertVaccin(form);
    }
    @PatchMapping("vaccin/{id:[0-9]+}")
    public void updateVaccin(@PathVariable Long id, @RequestBody VaccinForm form){
        santeService.updateVaccin(id, form);
    }
    @GetMapping("vaccin/all")
    public Set<VaccinDTO> getAllVaccin(){
        return santeService.getAllVaccin();
    }

    //Maladie

    @GetMapping("/maladie/a/{id:[0-9]+}")
    public Set<ADTO>getAllA(@PathVariable Long id){
        return santeService.getAllA(id);
    }

    @GetMapping("/maladie/all")
    public Set<MaladieDTO>getAllMaladie(){
        return santeService.getAllMaladie();
    }

    @GetMapping("/maladie/{id:[0-9]+}")
    public MaladieDTO getMaladie(@PathVariable Long id){
        return santeService.getMaladie(id);
    }

    @PostMapping("/maladie/add")
    public void updateMaladie(@RequestBody String nom){
        santeService.insertMaladie(nom);
    }

    @PatchMapping("/maladie/{id:[0-9]+}")
    public void updateMaladie(@PathVariable Long id, @RequestBody MaladieForm form){
        santeService.updateMaladie(id, form.getNom());
    }

    //Traitement

    @GetMapping("/traitement/all")
    public Set<TraitementDTO>getAllTraitement(){
        return santeService.getAllTraitement();
    }
    @GetMapping("/traitement/{id:[0-9]+}")
    public TraitementDTO getTraitement(@PathVariable Long id){
        return santeService.getTraitement(id);
    }
    @PostMapping("/traitement/add")
    public void updateTraitement(@RequestBody TraitementForm form){
        santeService.insertTraitement(form);
    }
    @PatchMapping("/traitement/{id:[0-9]+}")
    public void updateMaladie(@PathVariable Long id, @RequestBody TraitementForm form){
        santeService.updateTraitement(id, form);
    }
}
