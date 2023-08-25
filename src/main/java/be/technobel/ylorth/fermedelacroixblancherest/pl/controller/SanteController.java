package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.bll.models.Vaccination;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.*;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.AForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.MaladieForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.TraitementForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.sante.VaccinForm;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante.SanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<Set<Vaccination>> getCarnetVaccination(@PathVariable Long id){
        return ResponseEntity.ok(santeService.getCarnetVaccination(id));
    }
    @PostMapping("/vaccination/{id:[0-9]+}")
    public void vacciante(@PathVariable Long id, @RequestBody String nom){
        santeService.insertInjection(id,nom);
    }
    @GetMapping("/vaccin/{nom}")
    public ResponseEntity<Vaccin> getVaccin(@PathVariable String nom){
        return ResponseEntity.ok(Vaccin.fromBLL(santeService.getVaccin(nom)));
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
    public ResponseEntity<Set<Vaccin>> getAllVaccin(){
        return ResponseEntity.ok(santeService.getAllVaccin().stream().map(Vaccin::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("vaccination/liste/{id:[0-9]+}")
    public ResponseEntity<List<String>> toVaccinate(@PathVariable Long id){
        return ResponseEntity.ok(santeService.toVaccinate(id));
    }



    //Maladie

    @GetMapping("/maladie/a/{id:[0-9]+}")
    public ResponseEntity<Set<A>>getAllA(@PathVariable Long id){
        return ResponseEntity.ok(santeService.getAllA(id).stream().map(A::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("/maladie/a/one/{id:[0-9]+}")
    public ResponseEntity<A> getOneA(@PathVariable Long id){
        return ResponseEntity.ok(A.fromBLL(santeService.getOneA(id)));
    }

    @PostMapping("/maladie/a/{id:[0-9]+}")
    public void insertA(@PathVariable Long id, @RequestBody AForm form){
        santeService.insertA(id, form);
    }

    @PatchMapping("/maladie/a/{id:[0-9]+}")
    public void updateA(@PathVariable Long id, @RequestBody AForm form){
        santeService.updateA(id,form);
    }

    @DeleteMapping("/maladie/a/{id:[0-9]+}")
    public void deleteA(@PathVariable Long id){
        santeService.deleteA(id);
    }

    @GetMapping("/maladie/all")
    public ResponseEntity<Set<Maladie>>getAllMaladie(){
        return ResponseEntity.ok(santeService.getAllMaladie().stream().map(Maladie::fromBLL).collect(Collectors.toSet()));
    }

    @GetMapping("/maladie/{id:[0-9]+}")
    public ResponseEntity<Maladie> getMaladie(@PathVariable Long id){
        return ResponseEntity.ok(Maladie.fromBLL(santeService.getMaladie(id)));
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
    public ResponseEntity<Set<Traitement>>getAllTraitement(){
        return ResponseEntity.ok(santeService.getAllTraitement().stream().map(Traitement::fromBLL).collect(Collectors.toSet()));
    }
    @GetMapping("/traitement/{id:[0-9]+}")
    public ResponseEntity<Traitement> getTraitement(@PathVariable Long id){
        return ResponseEntity.ok(Traitement.fromBLL(santeService.getTraitement(id)));
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
