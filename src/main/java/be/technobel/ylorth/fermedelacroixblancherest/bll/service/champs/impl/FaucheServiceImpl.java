package be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.impl;

import be.technobel.ylorth.fermedelacroixblancherest.exception.FaucheInsertException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.Fauche;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.FaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.TypeDeGrainsRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.FaucheService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FaucheServiceImpl implements FaucheService {

    private final ChampRepository champRepository;
    private final TypeDeGrainsRepository typeDeGrainsRepository;
    private final CultureRepository cultureRepository;
    private final FaucheRepository faucheRepository;

    public FaucheServiceImpl(ChampRepository champRepository, TypeDeGrainsRepository typeDeGrainsRepository, CultureRepository cultureRepository,
                             FaucheRepository faucheRepository) {
        this.champRepository = champRepository;
        this.typeDeGrainsRepository = typeDeGrainsRepository;
        this.cultureRepository = cultureRepository;
        this.faucheRepository = faucheRepository;
    }

    @Override
    public FaucheEntity getOne(Long id) {
        return faucheRepository.findById(id).get();
    }

    @Override
    public void insert(FaucheInsertForm form) {
        if(form!=null){

            Optional<CultureEntity> cultureCorrespondante = cultureRepository.findAllByChamp(form.getChampId()).stream()
                    .filter(culture -> culture.getDateMiseEnCulture().getYear()== form.getAnnee())
                    .findFirst();

            FaucheEntity entity = new FaucheEntity();
            if(!faucheRepository.existsByAnneeAndCultureId(form.getAnnee(),cultureCorrespondante.get().getId())){
                entity.setCulture(cultureRepository.findById(cultureCorrespondante.get().getId()).get());
                entity.setAnnee(form.getAnnee());
                entity.setFauche1(form.getFauche());
                entity.setFauche1rendement(form.getFaucheRendement());
            } else {
                entity = faucheRepository.findByAnneeAndCulture(form.getAnnee(),cultureCorrespondante.get().getId());
                if(entity.getFauche2()==null){
                    entity.setFauche2(form.getFauche());
                    entity.setFauche2rendement(form.getFaucheRendement());
                } else if(entity.getFauche3()==null){
                    entity.setFauche3(form.getFauche());
                    entity.setFauche3rendement(form.getFaucheRendement());
                } else if(entity.getFauche4()==null){
                    entity.setFauche4(form.getFauche());
                    entity.setFauche4rendement(form.getFaucheRendement());
                } else {
                    throw new FaucheInsertException("Nombre de fauches max atteintes");
                }
            }
            faucheRepository.save(entity);
        }
    }

    @Override
    public void update(Long id, FaucheUpdateForm form) {
        if(form!=null){
            FaucheEntity entity = faucheRepository.findById(id).get();
            entity.setCulture(cultureRepository.findById(form.getCultureId()).get());
            entity.setAnnee(form.getAnnee());
            entity.setFauche1(form.getFauche1());
            entity.setFauche1rendement(form.getFauche1Rendement());
            entity.setFauche2(form.getFauche2());
            entity.setFauche2rendement(form.getFauche2Rendement());
            entity.setFauche3(form.getFauche3());
            entity.setFauche3rendement(form.getFauche3Rendement());
            entity.setFauche4(form.getFauche4());
            entity.setFauche4rendement(form.getFauche4Rendement());
            faucheRepository.save(entity);
        }
    }

    @Override
    public Set<FaucheEntity> getAll(String nomChamp) {
        return faucheRepository.findAllByChamp(nomChamp).stream()
                .collect(Collectors.toSet());
    }
    @Override
    public Set<FaucheEntity> getAll(int annee) {
        return faucheRepository.findAllByAnnee(annee).stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getAllAnnee() {
        return faucheRepository.getAllAnnee();
    }
}
