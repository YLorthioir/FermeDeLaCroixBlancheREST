package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule.VehiculeService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Vehicule;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vehicules.VehiculeRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.VehiculeForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements VehiculeService {
    
    private final VehiculeRepository vehiculeRepository;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public Optional<Vehicule> getOne(String plaque) {
        return vehiculeRepository.findById(plaque);
    }

    @Override
    public List<Vehicule> getAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public void create(VehiculeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");
        
        if(vehiculeRepository.existsById(form.plaque()))
            throw new AlreadyExistsException("Plaque déjà existante");
        
        Vehicule vehicule = new Vehicule();
        vehicule.setPlaque(form.plaque());
        vehicule.setMarque(form.marque());
        vehicule.setModele(form.modele());
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void update(VehiculeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");

        if(vehiculeRepository.existsById(form.plaque()))
            throw new AlreadyExistsException("Plaque déjà existante");

        Vehicule vehicule = vehiculeRepository.findById(form.plaque()).orElseThrow(()->new NotFoundException("Véhicule non trouvé"));
        vehicule.setMarque(form.marque());
        vehicule.setModele(form.modele());
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void delete(String plaque) {
        vehiculeRepository.deleteById(plaque);
    }
}
