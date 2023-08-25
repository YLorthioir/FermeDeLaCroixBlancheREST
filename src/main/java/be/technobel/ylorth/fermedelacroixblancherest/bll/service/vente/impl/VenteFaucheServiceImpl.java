package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.impl;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFauche;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteFaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFaucheForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.FaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vente.VenteFaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.VenteFaucheService;
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
    public List<VenteFaucheEntity> getAll() {
        return venteFaucheRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public VenteFaucheEntity getOne(Long id) {
        return venteFaucheRepository.findById(id).get();
    }

    @Override
    public void insert(VenteFaucheForm form) {
        if(form!=null){
            VenteFaucheEntity entity = new VenteFaucheEntity();
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
            VenteFaucheEntity entity = venteFaucheRepository.findById(id).get();
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
