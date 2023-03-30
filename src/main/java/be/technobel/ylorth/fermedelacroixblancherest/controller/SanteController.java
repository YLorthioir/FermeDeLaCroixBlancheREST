package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.ADTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.sante.Vaccination;
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


    @GetMapping("/vaccin/{id:[0-9]+}")
    public Set<Vaccination> getCarnetVaccination(@PathVariable Long id){
        return santeService.getCarnetVaccination(id);
    }

    @GetMapping("/maladie/{id:[0-9]+}")
    public Set<ADTO>getAllMaladie(@PathVariable Long id){
        return santeService.getAllA(id);
    }
}
