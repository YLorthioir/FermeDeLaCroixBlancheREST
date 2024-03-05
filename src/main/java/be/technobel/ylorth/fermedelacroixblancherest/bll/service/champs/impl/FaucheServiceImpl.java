package be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.impl;

import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.FaucheInsertException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.FaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.FaucheService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FaucheServiceImpl implements FaucheService {

    private final CultureRepository cultureRepository;
    private final FaucheRepository faucheRepository;

    public FaucheServiceImpl(CultureRepository cultureRepository,
                             FaucheRepository faucheRepository) {
        this.cultureRepository = cultureRepository;
        this.faucheRepository = faucheRepository;
    }

    /**
     * Récupère un enregistrement de fauche spécifié par son ID depuis le référentiel.
     *
     * Cette méthode prend l'ID d'un enregistrement de fauche en entrée, recherche cet enregistrement dans le référentiel des fauches,
     * puis le retourne s'il est trouvé. Si l'enregistrement de fauche n'est pas trouvé, elle lève une exception NotFoundException.
     *
     * @param id L'ID de l'enregistrement de fauche à récupérer.
     * @return L'enregistrement de fauche (FaucheEntity) correspondant à l'ID spécifié.
     * @throws NotFoundException Si l'enregistrement de fauche n'est pas trouvé dans le référentiel.
     */
    @Override
    public FaucheEntity getOne(Long id) {
        return faucheRepository.findById(id).orElseThrow(()->new NotFoundException("Fauche not found"));
    }

    /**
     * Insère un nouvel enregistrement de fauche en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode prend un formulaire (FaucheInsertForm) en entrée, vérifie que le formulaire n'est pas null, puis crée un nouvel
     * enregistrement de fauche en utilisant les informations fournies dans le formulaire. L'enregistrement de fauche est associé à une culture
     * correspondante, déterminée en fonction de l'année spécifiée dans le formulaire et du champ auquel elle est liée.
     *
     * Si une culture correspondante est trouvée, la méthode vérifie le nombre de fauches déjà enregistrées pour cette culture et l'année
     * spécifiée, puis insère la fauche dans le premier emplacement disponible (fauche1, fauche2, fauche3, fauche4) avec son rendement associé.
     * Si le nombre maximal de fauches est atteint (4), elle lève une exception FaucheInsertException.
     *
     * @param form Le formulaire contenant les informations pour créer un nouvel enregistrement de fauche.
     * @throws NotFoundException Si la culture correspondante n'est pas trouvée dans le référentiel.
     * @throws FaucheInsertException Si le nombre maximal de fauches (4) pour la culture et l'année spécifiée est atteint.
     */
    @Override
    public void insert(FaucheInsertForm form) {
        if(form!=null){

            Specification<CultureEntity> specificationCulture = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("champ").get("id"),form.champId())));

            Optional<CultureEntity> cultureCorrespondante = cultureRepository.findAll(specificationCulture).stream()
                    .filter(culture -> culture.getDateMiseEnCulture().getYear()== form.annee())
                    .findFirst();

            FaucheEntity entity = new FaucheEntity();

            Specification<FaucheEntity> specificationAnneeCulture = (((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("annee"),form.annee()), criteriaBuilder.equal(root.get("culture").get("id"),cultureCorrespondante.orElseThrow(()->new NotFoundException("Culture not found")).getId()))));

            if(!faucheRepository.exists(specificationAnneeCulture)){
                entity.setCulture(cultureRepository.findById(cultureCorrespondante.orElseThrow(()->new NotFoundException("Culture not found")).getId()).orElseThrow(()->new NotFoundException("Culture not found")));
                entity.setAnnee(form.annee());
                entity.setFauche1(form.fauche());
                entity.setFauche1rendement(form.faucheRendement());
            } else {
                entity = faucheRepository.findOne(specificationAnneeCulture).orElseThrow(()->new NotFoundException("Culture not found"));
                if(entity.getFauche2()==null){
                    entity.setFauche2(form.fauche());
                    entity.setFauche2rendement(form.faucheRendement());
                } else if(entity.getFauche3()==null){
                    entity.setFauche3(form.fauche());
                    entity.setFauche3rendement(form.faucheRendement());
                } else if(entity.getFauche4()==null){
                    entity.setFauche4(form.fauche());
                    entity.setFauche4rendement(form.faucheRendement());
                } else {
                    throw new FaucheInsertException("Nombre de fauches max atteintes");
                }
            }
            faucheRepository.save(entity);
        }
    }

    /**
     * Met à jour un enregistrement de fauche spécifié par son ID en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode prend l'ID d'un enregistrement de fauche en entrée, ainsi qu'un formulaire (FaucheUpdateForm) contenant les nouvelles informations
     * pour la fauche. Elle vérifie que le formulaire n'est pas null, recherche l'enregistrement de fauche correspondant dans le référentiel des fauches,
     * puis met à jour ses informations en conséquence.
     *
     * @param id L'ID de l'enregistrement de fauche à mettre à jour.
     * @param form Le formulaire contenant les nouvelles informations pour la fauche.
     * @throws NotFoundException Si l'enregistrement de fauche ou la culture spécifiée dans le formulaire n'est pas trouvée dans le référentiel.
     */
    @Override
    public void update(Long id, FaucheUpdateForm form) {
        if(form!=null){
            FaucheEntity entity = faucheRepository.findById(id).orElseThrow(()->new NotFoundException("Fauche not found"));
            entity.setCulture(cultureRepository.findById(form.cultureId()).orElseThrow(()->new NotFoundException("Culture not found")));
            entity.setAnnee(form.annee());
            entity.setFauche1(form.fauche1());
            entity.setFauche1rendement(form.fauche1Rendement());
            entity.setFauche2(form.fauche2());
            entity.setFauche2rendement(form.fauche2Rendement());
            entity.setFauche3(form.fauche3());
            entity.setFauche3rendement(form.fauche3Rendement());
            entity.setFauche4(form.fauche4());
            entity.setFauche4rendement(form.fauche4Rendement());
            faucheRepository.save(entity);
        }
    }

    /**
     * Récupère tous les enregistrements de fauche associés à un champ spécifié par son nom.
     *
     * Cette méthode prend le nom d'un champ en entrée, interroge le référentiel des fauches pour obtenir tous les enregistrements de fauche
     * associés à ce champ, puis les retourne dans un ensemble (Set) de FaucheEntity.
     *
     * @param nomChamp Le nom du champ pour lequel les enregistrements de fauche doivent être récupérés.
     * @return Un ensemble (Set) de FaucheEntity représentant tous les enregistrements de fauche associés au champ spécifié.
     */
    @Override
    public Set<FaucheEntity> getAll(String nomChamp) {

        Specification<FaucheEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("culture").get("champ").get("lieu"), nomChamp)));

        return new HashSet<>(faucheRepository.findAll(spec));
    }

    /**
     * Récupère tous les enregistrements de fauche pour une année spécifiée.
     *
     * Cette méthode prend une année en entrée, interroge le référentiel des fauches pour obtenir tous les enregistrements de fauche
     * associés à cette année, puis les retourne dans un ensemble (Set) de FaucheEntity.
     *
     * @param annee L'année pour laquelle les enregistrements de fauche doivent être récupérés.
     * @return Un ensemble (Set) de FaucheEntity représentant tous les enregistrements de fauche associés à l'année spécifiée.
     */
    @Override
    public Set<FaucheEntity> getAll(int annee) {

        Specification<FaucheEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("annee"), annee)));

        return new HashSet<>(faucheRepository.findAll(spec));
    }

    /**
     * Récupère toutes les années pour lesquelles des enregistrements de fauche sont disponibles.
     *
     * Cette méthode interroge le référentiel des fauches pour obtenir la liste de toutes les années pour lesquelles
     * des enregistrements de fauche sont disponibles, puis les retourne dans un ensemble (Set) de valeurs entières (Integer).
     *
     * @return Un ensemble (Set) de valeurs entières (Integer) représentant toutes les années pour lesquelles des enregistrements de fauche sont disponibles.
     */
    @Override
    public Set<Integer> getAllAnnee() {

        Specification<FaucheEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("annee"),root.get("annee")));

        return faucheRepository.findAll(specification).stream().map(FaucheEntity::getAnnee).collect(Collectors.toSet());
    }
}
