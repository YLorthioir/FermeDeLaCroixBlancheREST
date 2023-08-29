package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovin;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFauche;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovinForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFaucheForm;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.VenteBovinService;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.VenteFaucheService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/vente")
public class VenteController {

    private final VenteBovinService venteBovinService;
    private final VenteFaucheService venteFaucheService;

    public VenteController(VenteBovinService venteBovinService, VenteFaucheService venteFaucheService) {
        this.venteBovinService = venteBovinService;
        this.venteFaucheService = venteFaucheService;
    }

    // BovinEntity

    @GetMapping("/bovin/all")
    public ResponseEntity<List<VenteBovin>> getAllVenteBovin(){
        return ResponseEntity.ok(venteBovinService.getAll().stream().map(VenteBovin::fromBLL).toList());
    }

    @GetMapping("/bovin/{id:[0-9]+}")
    public ResponseEntity<VenteBovin> getVenteBovin(@PathVariable Long id){
        return ResponseEntity.ok(VenteBovin.fromBLL(venteBovinService.getOne(id)));
    }

    @PatchMapping("/bovin/update/{id:[0-9]+}")
    public void updateVenteBovin(@PathVariable Long id, @RequestBody @Valid VenteBovinForm form){
        venteBovinService.update(id, form);
    }

    @PostMapping("/bovin/add")
    public void addVenteBovin(@RequestBody @Valid VenteBovinForm form){
        venteBovinService.insert(form);
    }

    @DeleteMapping("/bovin/{id:[0-9]+}")
    public void deleteVenteBovin(@PathVariable Long id){
        venteBovinService.delete(id);
    }

    // Fauche

    @GetMapping("/fauche/all")
    public ResponseEntity<List<VenteFauche>> getAllVenteFauche(){
        return ResponseEntity.ok(venteFaucheService.getAll().stream().map(VenteFauche::fromBLL).toList());
    }

    @GetMapping("/fauche/{id:[0-9]+}")
    public ResponseEntity<VenteFauche> getVenteFauche(@PathVariable Long id){
        return ResponseEntity.ok(VenteFauche.fromBLL(venteFaucheService.getOne(id)));
    }

    @PatchMapping("/fauche/update/{id:[0-9]+}")
    public void updateVenteFauche(@PathVariable Long id, @RequestBody @Valid VenteFaucheForm form){
        venteFaucheService.update(id, form);
    }

    @PostMapping("/fauche/add")
    public void addVenteFauche(@RequestBody @Valid VenteFaucheForm form){
        venteFaucheService.insert(form);
    }

    @DeleteMapping("/fauche/{id:[0-9]+}")
    public void deleteVenteFauche(@PathVariable Long id){
        venteFaucheService.delete(id);
    }
}
