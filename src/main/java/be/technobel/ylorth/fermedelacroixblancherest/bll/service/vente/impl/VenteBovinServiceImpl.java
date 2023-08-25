package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.impl;

import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovin;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteBovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovinForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.BovinEngraissementRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vente.VenteBovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.VenteBovinService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenteBovinServiceImpl implements VenteBovinService {

    private final VenteBovinRepository venteBovinRepository;
    private final BovinEngraissementRepository bovinEngraissementRepository;

    public VenteBovinServiceImpl(VenteBovinRepository venteBovinRepository,
                                 BovinEngraissementRepository bovinEngraissementRepository) {
        this.venteBovinRepository = venteBovinRepository;
        this.bovinEngraissementRepository = bovinEngraissementRepository;
    }

    @Override
    public List<VenteBovinEntity> getAll() {
        return venteBovinRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public VenteBovinEntity getOne(Long id) {
        if(venteBovinRepository.existsById(id))
            return venteBovinRepository.findById(id).get();
        else
            return null;
    }

    @Override
    public void insert(VenteBovinForm form) {
        if(form!= null){
            VenteBovinEntity entity = new VenteBovinEntity();

            entity.setBovinEngraissement(bovinEngraissementRepository.findByNumeroInscription(form.getNumeroIdentification()).get());
            entity.setDateDeVente(form.getDate());
            entity.setQuantite(form.getQtt());
            entity.setPrixRevente(form.getPrixRevente());
            entity.setPrixCoutant(form.getPrixCoutant());
            venteBovinRepository.save(entity);
        }

    }

    @Override
    public void update(Long id, VenteBovinForm form) {
        if(form!= null){
            VenteBovinEntity entity = venteBovinRepository.findById(id).get();

            entity.setBovinEngraissement(bovinEngraissementRepository.findByNumeroInscription(form.getNumeroIdentification()).get());
            entity.setDateDeVente(form.getDate());
            entity.setQuantite(form.getQtt());
            entity.setPrixRevente(form.getPrixRevente());
            entity.setPrixCoutant(form.getPrixCoutant());
            venteBovinRepository.save(entity);
        }
    }

    @Override
    public void delete(Long id) {
        venteBovinRepository.deleteById(id);
    }
}
