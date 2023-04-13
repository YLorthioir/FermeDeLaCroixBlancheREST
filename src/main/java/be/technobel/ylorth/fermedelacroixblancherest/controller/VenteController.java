package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteBovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteFaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.vente.VenteBovinForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.vente.VenteFaucheForm;
import be.technobel.ylorth.fermedelacroixblancherest.service.vente.VenteBovinService;
import be.technobel.ylorth.fermedelacroixblancherest.service.vente.VenteFaucheService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Bovin

    @GetMapping("/bovin/all")
    public List<VenteBovinDTO> getAllVenteBovin(){
        return venteBovinService.getAll();
    }

    @GetMapping("/bovin/{id:[0-9]+}")
    public VenteBovinDTO getVenteBovin(@PathVariable Long id){
        return venteBovinService.getOne(id);
    }

    @PatchMapping("/bovin/update/{id:[0-9]+}")
    public void updateVenteBovin(@PathVariable Long id, @RequestBody VenteBovinForm form){
        venteBovinService.update(id, form);
    }

    @PostMapping("/bovin/add")
    public void addVenteBovin(@RequestBody VenteBovinForm form){
        venteBovinService.insert(form);
    }

    @DeleteMapping("/bovin/{id:[0-9]+}")
    public void deleteVenteBovin(@PathVariable Long id){
        venteBovinService.delete(id);
    }

    // Fauche

    @GetMapping("/fauche/all")
    public List<VenteFaucheDTO> getAllVenteFauche(){
        return venteFaucheService.getAll();
    }

    @GetMapping("/fauche/{id:[0-9]+}")
    public VenteFaucheDTO getVenteFauche(@PathVariable Long id){
        return venteFaucheService.getOne(id);
    }

    @PatchMapping("/fauche/update/{id:[0-9]+}")
    public void updateVenteFauche(@PathVariable Long id, @RequestBody VenteFaucheForm form){
        venteFaucheService.update(id, form);
    }

    @PostMapping("/fauche/add")
    public void addVenteFauche(@RequestBody VenteFaucheForm form){
        venteFaucheService.insert(form);
    }

    @DeleteMapping("/fauche/{id:[0-9]+}")
    public void deleteVenteFauche(@PathVariable Long id){
        venteFaucheService.delete(id);
    }
}
