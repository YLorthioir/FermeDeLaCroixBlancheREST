package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vehicule.InterventionService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vehicules.Intervention;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vehicules.InterventionRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vehicules.VehiculeRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vehicule.InterventionForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterventionServiceImpl implements InterventionService {
    
    private final InterventionRepository interventionRepository;
    private final VehiculeRepository vehiculeRepository;

    public InterventionServiceImpl(InterventionRepository interventionRepository,
                                   VehiculeRepository vehiculeRepository) {
        this.interventionRepository = interventionRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public List<Intervention> getAll(String plaque) {
        Specification<Intervention> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("vehicule").get("plaque"), plaque);
        
        return interventionRepository.findAll(specification);
    }

    @Override
    public void save(InterventionForm form) {
        if(form == null)
            throw new IllegalArgumentException("form can't be null");
        
        Intervention intervention = new Intervention();
        intervention.setDescription(form.description());
        intervention.setHeuresDeTravail(form.heuresDeTravail());
        intervention.setVehicule(vehiculeRepository.findById(form.vehicule()).orElseThrow(()-> new NotFoundException("Véhicule pas trouvé")));
        interventionRepository.save(intervention);
    }

    @Override
    public void delete(Long id) {
        interventionRepository.deleteById(id);
    }

    @Override
    public void update(Long id, InterventionForm form) {
        if(form == null)
            throw new IllegalArgumentException("form can't be null");

        Intervention intervention = interventionRepository.findById(id).orElseThrow(()-> new NotFoundException("Intervention not found"));
        intervention.setDescription(form.description());
        intervention.setHeuresDeTravail(form.heuresDeTravail());
        intervention.setVehicule(vehiculeRepository.findById(form.vehicule()).orElseThrow(()-> new NotFoundException("Véhicule pas trouvé")));
        interventionRepository.save(intervention);
    }
}
