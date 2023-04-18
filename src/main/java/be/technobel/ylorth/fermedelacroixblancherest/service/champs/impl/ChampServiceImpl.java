package be.technobel.ylorth.fermedelacroixblancherest.service.champs.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.ChampDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.CultureDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.champs.TypeDeGrainDTO;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Champ;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.Culture;
import be.technobel.ylorth.fermedelacroixblancherest.model.entity.champs.TypeDeGrain;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.model.form.champs.CultureForm;
import be.technobel.ylorth.fermedelacroixblancherest.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.repository.champs.TypeDeGrainsRepository;
import be.technobel.ylorth.fermedelacroixblancherest.service.champs.ChampService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChampServiceImpl implements ChampService {

    private final ChampRepository champRepository;
    private final TypeDeGrainsRepository typeDeGrainsRepository;
    private final CultureRepository cultureRepository;

    public ChampServiceImpl(ChampRepository champRepository,
                            TypeDeGrainsRepository typeDeGrainsRepository,
                            CultureRepository cultureRepository) {
        this.champRepository = champRepository;
        this.typeDeGrainsRepository = typeDeGrainsRepository;
        this.cultureRepository = cultureRepository;
    }

    // Champ

    @Override
    public Set<ChampDTO> getAll() {
        return champRepository.findAll().stream()
                .map(ChampDTO::toDTO)
                .collect(Collectors.toSet());
    }
    @Override
    public ChampDTO getChamp(Long id) {
        return ChampDTO.toDTO(champRepository.findById(id).get());
    }
    @Override
    public void insertChamp(ChampInsertForm form) {
        if(champRepository.existsChampByLieu(form.getLieu()))
            throw new AlreadyExistsException("Champ déjà existant");

        if(form!=null){
            Champ entity = new Champ();
            entity.setLieu(form.getLieu());
            entity.setSuperficie(form.getSuperficie());
            champRepository.save(entity);
        }

    }

    @Override
    public void updateChamp(Long id, ChampUpdateForm form) {
        if(form!=null){
            Champ entity = champRepository.findById(id).get();
            entity.setLieu(form.getLieu());
            entity.setSuperficie(form.getSuperficie());
            entity.setDateDerniereChaux(form.getDateDerniereChaux());
            champRepository.save(entity);
        }
    }

    // Culture

    @Override
    public void insertCulture(CultureForm form){
        if(form!=null){
            Culture entity = new Culture();
            entity.setChamp(champRepository.findById(form.getIdChamp()).get());
            entity.setDateMiseEnCulture(form.getDateMiseEnCulture());
            entity.setEstTemporaire(form.isTemporaire());
            entity.setTypeDeGrain(typeDeGrainsRepository.findById(form.getGrainId()).get());
            entity.setDateDeFin(form.getDateDeFin()!=null?form.getDateDeFin():null);
            entity.setDateEpandage(form.getDateDernierEpandage()!=null?form.getDateDernierEpandage():null);
            entity.setQttFumier(form.getQttFumier());
            entity.setAnalysePDF(form.getReferenceAnalyse());

            entity= cultureRepository.save(entity);
        }
    }
    @Override
    public CultureDTO getCulture(Long id) {
        return CultureDTO.toDTO(cultureRepository.findById(id).get());
    }
    @Override
    public void updateCulture(Long id, CultureForm form) {
        if(form!=null){
            Culture entity = cultureRepository.findById(id).get();
            entity.setChamp(champRepository.findById(form.getIdChamp()).get());
            entity.setDateMiseEnCulture(form.getDateMiseEnCulture());
            entity.setEstTemporaire(form.isTemporaire());
            entity.setTypeDeGrain(typeDeGrainsRepository.findById(form.getGrainId()).get());
            entity.setDateDeFin(form.getDateDeFin()!=null?form.getDateDeFin():null);
            entity.setDateEpandage(form.getDateDernierEpandage()!=null?form.getDateDernierEpandage():null);
            entity.setQttFumier(form.getQttFumier());
            entity.setAnalysePDF(form.getReferenceAnalyse());

            entity= cultureRepository.save(entity);
        }
    }
    @Override
    public Set<CultureDTO> getHistorique(Long id) {
        return cultureRepository.findAllByChamp(id).stream()
                .map(CultureDTO::toDTO)
                .collect(Collectors.toSet());
    }

    // Grains

    @Override
    public void updateGrain(Long id, String nom) {
        if(nom != null){
            TypeDeGrain entity = typeDeGrainsRepository.findById(id).get();
            entity.setNomGrain(nom);
            typeDeGrainsRepository.save(entity);
        }
    }

    @Override
    public void insertGrain(String nom) {
        TypeDeGrain entity = new TypeDeGrain();
        entity.setNomGrain(nom);
        typeDeGrainsRepository.save(entity);
    }

    @Override
    public Set<TypeDeGrainDTO> getAllGrains() {
        return typeDeGrainsRepository.findAll().stream()
                .map(TypeDeGrainDTO::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public TypeDeGrainDTO getOneGrain(Long id) {
        return TypeDeGrainDTO.toDTO(typeDeGrainsRepository.findById(id).get());
    }
}
