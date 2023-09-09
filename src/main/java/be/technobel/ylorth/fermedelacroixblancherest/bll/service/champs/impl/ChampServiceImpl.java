package be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.ChampService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.CultureForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.TypeDeGrainsRepository;
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
    public Set<ChampEntity> getAll() {
        return champRepository.findAll().stream()
                .collect(Collectors.toSet());
    }
    @Override
    public ChampEntity getChamp(Long id) {
        return champRepository.findById(id).get();
    }
    @Override
    public void insertChamp(ChampInsertForm form) {
        if(champRepository.existsByLieu(form.getLieu()))
            throw new AlreadyExistsException("Champ déjà existant");

        if(form!=null){
            ChampEntity entity = new ChampEntity();
            entity.setLieu(form.getLieu());
            entity.setSuperficie(form.getSuperficie());
            champRepository.save(entity);
        }

    }

    @Override
    public void updateChamp(Long id, ChampUpdateForm form) {
        if(form!=null){
            ChampEntity entity = champRepository.findById(id).get();
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
            CultureEntity entity = new CultureEntity();
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
    public CultureEntity getCulture(Long id) {
        return cultureRepository.findById(id).get();
    }
    @Override
    public void updateCulture(Long id, CultureForm form) {
        if(form!=null){
            CultureEntity entity = cultureRepository.findById(id).get();
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
    public Set<CultureEntity> getHistorique(Long id) {
        return cultureRepository.findAllByChamp(id).stream()
                .collect(Collectors.toSet());
    }

    // Grains

    @Override
    public void updateGrain(Long id, String nom) {
        if(nom != null){
            TypeDeGrainEntity entity = typeDeGrainsRepository.findById(id).get();
            entity.setNomGrain(nom);
            typeDeGrainsRepository.save(entity);
        }
    }

    @Override
    public void insertGrain(String nom) {
        if(typeDeGrainsRepository.existsByNomGrain(nom))
            throw new AlreadyExistsException("Le grain existe déjà");

        TypeDeGrainEntity entity = new TypeDeGrainEntity();
        entity.setNomGrain(nom);
        typeDeGrainsRepository.save(entity);
    }

    @Override
    public Set<TypeDeGrainEntity> getAllGrains() {
        return typeDeGrainsRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public TypeDeGrainEntity getOneGrain(Long id) {
        return typeDeGrainsRepository.findById(id).get();
    }
}
