package be.technobel.ylorth.fermedelacroixblancherest.service.vente.impl;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteFaucheDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteFauche;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.vente.VenteFaucheForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.champs.FaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.vente.VenteFaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.vente.VenteFaucheService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenteFaucheServiceImpl implements VenteFaucheService {

    private final VenteFaucheRepository venteFaucheRepository;
    private final FaucheRepository faucheRepository;

    public VenteFaucheServiceImpl(VenteFaucheRepository venteFaucheRepository,
                                  FaucheRepository faucheRepository) {
        this.venteFaucheRepository = venteFaucheRepository;
        this.faucheRepository = faucheRepository;
    }

    @Override
    public List<VenteFaucheDTO> getAll() {
        return venteFaucheRepository.findAll().stream()
                .map(VenteFaucheDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VenteFaucheDTO getOne(Long id) {
        return VenteFaucheDTO.toDTO(venteFaucheRepository.findById(id).get());
    }

    @Override
    public void insert(VenteFaucheForm form) {
        if(form!=null){
            VenteFauche entity = new VenteFauche();
            entity.setFauche(faucheRepository.findById(form.getFaucheId()).get());
            entity.setQuantite(form.getQtt());
            entity.setDateDeVente(form.getDate());
            entity.setPrixCoutant(form.getPrixCoutant());
            entity.setPrixRevente(form.getPrixRevente());
            venteFaucheRepository.save(entity);
        }

    }

    @Override
    public void update(Long id, VenteFaucheForm form) {
        if(form!=null){
            VenteFauche entity = venteFaucheRepository.findById(id).get();
            entity.setFauche(faucheRepository.findById(form.getFaucheId()).get());
            entity.setQuantite(form.getQtt());
            entity.setDateDeVente(form.getDate());
            entity.setPrixCoutant(form.getPrixCoutant());
            entity.setPrixRevente(form.getPrixRevente());
            venteFaucheRepository.save(entity);
        }
    }

    @Override
    public void delete(Long id) {
        venteFaucheRepository.deleteById(id);
    }
}
