package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.bovins.BovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.service.bovins.BovinService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/bovin")
public class BovinController {

    private final BovinService bovinService;

    public BovinController(BovinService bovinService) {
        this.bovinService = bovinService;
    }

    @GetMapping("/all")
    public Set<BovinDTO> getAll(){
        return bovinService.getAll();
    }

    @GetMapping("/{numeroInscription}")
    public BovinDTO getOne(@PathVariable String numeroInscription){
        return bovinService.getOne(numeroInscription);
    }
}
