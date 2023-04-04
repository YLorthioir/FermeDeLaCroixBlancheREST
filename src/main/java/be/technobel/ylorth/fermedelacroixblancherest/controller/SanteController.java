package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.VaccinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.Vaccination;
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


    @GetMapping("/vaccination/{id:[0-9]+}")
    public Set<Vaccination> getCarnetVaccination(@PathVariable Long id){
        return santeService.getCarnetVaccination(id);
    }

    @GetMapping("/maladie/{id:[0-9]+}")
    public Set<ADTO>getAllMaladie(@PathVariable Long id){
        return santeService.getAllA(id);
    }

    @GetMapping("/vaccin/{nom}")
    public VaccinDTO getVaccin(@PathVariable String nom){
        return santeService.getVaccin(nom);
    }

    @PostMapping("/vaccination/{id:[0-9]+}")
    public void vacciante(@PathVariable Long id, @RequestBody String nom){
        santeService.insertInjection(id,nom);
    }
    @PostMapping("vaccin/")
    public void insertVaccin(@RequestBody VaccinForm form){
        santeService.insertVaccin(form);
    }
    @PostMapping("vaccin/{id:[0-9]+}")
    public void insertVaccin(@PathVariable Long id, @RequestBody VaccinForm form){
        santeService.updateVaccin(id, form);
    }

    @GetMapping("vaccin/all")
    public Set<VaccinDTO> getAllVaccin(){
        return santeService.getAllVaccin();
    }
}
