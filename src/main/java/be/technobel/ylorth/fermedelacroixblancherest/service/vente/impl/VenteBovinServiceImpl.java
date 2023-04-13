package be.technobel.ylorth.fermedelacroixblancherest.service.vente.impl;

import be.technobel.ylorth.fermedelacroixblancherest.model.dto.vente.VenteBovinDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.vente.VenteBovin;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.vente.VenteBovinForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.bovins.BovinEngraissementRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.vente.VenteBovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.vente.VenteBovinService;
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
    public List<VenteBovinDTO> getAll() {
        return venteBovinRepository.findAll().stream()
                .map(VenteBovinDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VenteBovinDTO getOne(Long id) {
        if(venteBovinRepository.existsById(id))
            return VenteBovinDTO.toDTO(venteBovinRepository.findById(id).get());
        else
            return null;
    }

    @Override
    public void insert(VenteBovinForm form) {
        if(form!= null){
            VenteBovin entity = new VenteBovin();

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
            VenteBovin entity = venteBovinRepository.findById(id).get();

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
