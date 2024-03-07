package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule.InterventionService;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule.VehiculeService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.InterventionDTO;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.InterventionForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.VehiculeDTO;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.VehiculeForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicule")
public class VehiculeController {
    private final VehiculeService vehiculeService;
    private final InterventionService interventionService;
    
    public VehiculeController(VehiculeService vehiculeService, InterventionService interventionService) {
        this.vehiculeService = vehiculeService;
        this.interventionService = interventionService;
    }

    @GetMapping("/{plaque}")
    public ResponseEntity<VehiculeDTO> getVehicule(@PathVariable String plaque) {
        return ResponseEntity.ok(vehiculeService.getOne(plaque).map(VehiculeDTO::fromEntity).orElseThrow(()-> new NotFoundException("Véhicule not found")));
    }

    @PutMapping("/update")
    public void updateVehicule(@RequestBody VehiculeForm form) {
        vehiculeService.update(form);
    }

    @DeleteMapping("/{plaque}")
    public void deleteVehicule(@PathVariable String plaque) {
        vehiculeService.delete(plaque);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehiculeDTO>> getAllVehicules() {
        return ResponseEntity.ok(vehiculeService.getAll().stream().map(VehiculeDTO::fromEntity).toList());
    }
    
    @PostMapping("/create")
    public void createVehicule(@RequestBody VehiculeForm form) {
        vehiculeService.create(form);
    }
    
    @GetMapping("/{plaque}/interventions")
    public ResponseEntity<List<InterventionDTO>> getInterventions(@PathVariable String plaque) {
        return ResponseEntity.ok(interventionService.getAll(plaque).stream().map(InterventionDTO::fromEntity).toList());
    }
    
    @PostMapping("/intervention/create")
    public void createIntervention(@RequestBody InterventionForm form) {
        interventionService.save(form);
    }
    
    @PostMapping("/intervention/update/{id:[0-9]+}")
    public void updateIntervention(@PathVariable Long id, @RequestBody InterventionForm form) {
        interventionService.update(id, form);
    }
    
    @DeleteMapping("/intervention/{id}")
    public void deleteIntervention(@PathVariable Long id) {
        interventionService.delete(id);
    }
}
