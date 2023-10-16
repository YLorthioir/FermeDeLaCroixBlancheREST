package be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.ChampService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.ChampInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.ChampUpdateForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.CultureForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.TypeDeGrainsRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    /**
     * Récupère l'ensemble des enregistrements de champs présents dans le référentiel.
     *
     * Cette méthode interroge le référentiel des champs pour obtenir tous les enregistrements de champs,
     * puis les retourne dans un ensemble (Set) de ChampEntity.
     *
     * @return Un ensemble (Set) de ChampEntity représentant tous les enregistrements de champs enregistrés.
     */
    @Override
    public Set<ChampEntity> getAll() {
        return champRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Récupère un enregistrement de champ spécifié par son ID depuis le référentiel.
     *
     * Cette méthode prend l'ID d'un enregistrement de champ en entrée, recherche cet enregistrement dans le référentiel des champs,
     * puis le retourne s'il est trouvé. Si l'enregistrement de champ n'est pas trouvé, elle lève une exception NotFoundException.
     *
     * @param id L'ID de l'enregistrement de champ à récupérer.
     * @return L'enregistrement de champ (ChampEntity) correspondant à l'ID spécifié.
     * @throws NotFoundException Si l'enregistrement de champ n'est pas trouvé dans le référentiel.
     */
    @Override
    public ChampEntity getChamp(Long id) {
        return champRepository.findById(id).orElseThrow(()-> new NotFoundException("Champ not found"));
    }

    /**
     * Insère un nouvel enregistrement de champ dans le référentiel en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode prend un formulaire (ChampInsertForm) en entrée, vérifie que le formulaire n'est pas null, et vérifie également
     * que le lieu du champ n'existe pas déjà dans le référentiel. Si le lieu du champ n'existe pas, elle crée un nouvel enregistrement
     * de champ en utilisant les informations fournies dans le formulaire.
     *
     * @param form Le formulaire contenant les informations pour créer un nouvel enregistrement de champ.
     * @throws AlreadyExistsException Si le lieu du champ spécifié existe déjà dans le référentiel.
     */
    @Override
    public void insertChamp(ChampInsertForm form) {

        Specification<ChampEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lieu"), form.lieu())));

        if(champRepository.exists(spec))
            throw new AlreadyExistsException("Champ déjà existant");

        if(form!=null){
            ChampEntity entity = new ChampEntity();
            entity.setLieu(form.lieu());
            entity.setSuperficie(form.superficie());
            champRepository.save(entity);
        }

    }

    /**
     * Met à jour les informations d'un enregistrement de champ spécifié par son ID en utilisant les données fournies dans le formulaire.
     *
     * Cette méthode prend l'ID d'un enregistrement de champ en entrée, ainsi qu'un formulaire (`ChampUpdateForm`) contenant les nouvelles informations
     * pour le champ. Elle vérifie que le formulaire n'est pas null, recherche l'enregistrement de champ correspondant dans le référentiel des champs,
     * puis met à jour ses informations en conséquence.
     *
     * @param id L'ID de l'enregistrement de champ à mettre à jour.
     * @param form Le formulaire contenant les nouvelles informations pour le champ.
     * @throws NotFoundException Si l'enregistrement de champ n'est pas trouvé dans le référentiel.
     */
    @Override
    public void updateChamp(Long id, ChampUpdateForm form) {
        if(form!=null){
            ChampEntity entity = champRepository.findById(id).orElseThrow(()-> new NotFoundException("Champ not found"));
            entity.setLieu(form.lieu());
            entity.setSuperficie(form.superficie());
            entity.setDateDerniereChaux(form.dateDerniereChaux());
            champRepository.save(entity);
        }
    }

    // Culture

    /**
     * Insère un nouvel enregistrement de culture dans le référentiel en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode prend un formulaire (`CultureForm`) en entrée, vérifie que le formulaire n'est pas null, puis crée un nouvel
     * enregistrement de culture en utilisant les informations fournies dans le formulaire.
     *
     * @param form Le formulaire contenant les informations pour créer un nouvel enregistrement de culture.
     * @throws NotFoundException Si le champ ou le type de grain spécifié dans le formulaire n'est pas trouvé dans le référentiel.
     */
    @Override
    public void insertCulture(CultureForm form){
        if(form!=null){
            CultureEntity entity = new CultureEntity();
            entity.setChamp(champRepository.findById(form.idChamp()).orElseThrow(()-> new NotFoundException("Champ not found")));
            entity.setDateMiseEnCulture(form.dateMiseEnCulture());
            entity.setEstTemporaire(form.temporaire());
            entity.setTypeDeGrain(typeDeGrainsRepository.findById(form.grainId()).orElseThrow(()-> new NotFoundException("Grain not found")));
            entity.setDateDeFin(form.dateDeFin()!=null?form.dateDeFin():null);
            entity.setDateEpandage(form.dateDernierEpandage()!=null?form.dateDernierEpandage():null);
            entity.setQttFumier(form.qttFumier());
            entity.setAnalysePDF(form.referenceAnalyse());

            entity= cultureRepository.save(entity);
        }
    }

    /**
     * Récupère un enregistrement de culture spécifié par son ID depuis le référentiel.
     *
     * Cette méthode prend l'ID d'un enregistrement de culture en entrée, recherche cet enregistrement dans le référentiel des cultures,
     * puis le retourne s'il est trouvé. Si l'enregistrement de culture n'est pas trouvé, elle lève une exception NotFoundException.
     *
     * @param id L'ID de l'enregistrement de culture à récupérer.
     * @return L'enregistrement de culture (CultureEntity) correspondant à l'ID spécifié.
     * @throws NotFoundException Si l'enregistrement de culture n'est pas trouvé dans le référentiel.
     */
    @Override
    public CultureEntity getCulture(Long id) {
        return cultureRepository.findById(id).orElseThrow(()-> new NotFoundException("Culture not found"));
    }

    /**
     * Met à jour les informations d'un enregistrement de culture spécifié par son ID en utilisant les données fournies dans le formulaire.
     *
     * Cette méthode prend l'ID d'un enregistrement de culture en entrée, ainsi qu'un formulaire (`CultureForm`) contenant les nouvelles informations
     * pour la culture. Elle vérifie que le formulaire n'est pas null, recherche l'enregistrement de culture correspondant dans le référentiel des cultures,
     * puis met à jour ses informations en conséquence.
     *
     * @param id L'ID de l'enregistrement de culture à mettre à jour.
     * @param form Le formulaire contenant les nouvelles informations pour la culture.
     * @throws NotFoundException Si l'enregistrement de culture, le champ ou le type de grain spécifié dans le formulaire n'est pas trouvé dans le référentiel.
     */
    @Override
    public void updateCulture(Long id, CultureForm form) {
        if(form!=null){
            CultureEntity entity = cultureRepository.findById(id).orElseThrow(()-> new NotFoundException("Culture not found"));
            entity.setChamp(champRepository.findById(form.idChamp()).orElseThrow(()-> new NotFoundException("Champ not found")));
            entity.setDateMiseEnCulture(form.dateMiseEnCulture());
            entity.setEstTemporaire(form.temporaire());
            entity.setTypeDeGrain(typeDeGrainsRepository.findById(form.grainId()).orElseThrow(()-> new NotFoundException("Grain not found")));
            entity.setDateDeFin(form.dateDeFin()!=null?form.dateDeFin():null);
            entity.setDateEpandage(form.dateDernierEpandage()!=null?form.dateDernierEpandage():null);
            entity.setQttFumier(form.qttFumier());
            entity.setAnalysePDF(form.referenceAnalyse());

            entity= cultureRepository.save(entity);
        }
    }

    /**
     * Récupère l'historique des enregistrements de culture associés à un champ spécifié par son ID.
     *
     * Cette méthode prend l'ID d'un champ en entrée, interroge le référentiel des cultures pour obtenir tous les enregistrements de culture
     * associés à ce champ, puis les retourne dans un ensemble (Set) de CultureEntity.
     *
     * @param id L'ID du champ pour lequel l'historique des cultures doit être récupéré.
     * @return Un ensemble (Set) de CultureEntity représentant l'historique des enregistrements de culture associés au champ spécifié.
     */
    @Override
    public Set<CultureEntity> getHistorique(Long id) {

        Specification<CultureEntity> specification = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("champ").get("id"),id)));

        return new HashSet<>(cultureRepository.findAll(specification));
    }

    // Grains

    /**
     * Met à jour le nom d'un type de grain spécifié par son ID en utilisant le nouveau nom fourni.
     *
     * Cette méthode prend l'ID d'un type de grain en entrée, ainsi qu'un nouveau nom pour le type de grain. Elle vérifie que le nouveau nom
     * n'est pas null, recherche le type de grain correspondant dans le référentiel des types de grains, puis met à jour son nom en conséquence.
     *
     * @param id L'ID du type de grain à mettre à jour.
     * @param nom Le nouveau nom pour le type de grain.
     * @throws NotFoundException Si le type de grain n'est pas trouvé dans le référentiel.
     */
    @Override
    public void updateGrain(Long id, String nom) {
        Specification<TypeDeGrainEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nomGrain"), nom)));
        if(typeDeGrainsRepository.exists(spec))
            throw new AlreadyExistsException("Le grain existe déjà");

        if(nom != null){
            TypeDeGrainEntity entity = typeDeGrainsRepository.findById(id).orElseThrow(()-> new NotFoundException("Grain not found"));
            entity.setNomGrain(nom);
            typeDeGrainsRepository.save(entity);
        }
    }

    /**
     * Insère un nouveau type de grain dans le référentiel en utilisant le nom de grain fourni.
     *
     * Cette méthode prend le nom d'un nouveau type de grain en entrée, vérifie que le type de grain avec ce nom n'existe pas déjà
     * dans le référentiel, puis crée un nouveau type de grain en utilisant le nom spécifié.
     *
     * @param nom Le nom du nouveau type de grain à insérer.
     * @throws AlreadyExistsException Si un type de grain avec le même nom existe déjà dans le référentiel.
     */
    @Override
    public void insertGrain(String nom) {

        Specification<TypeDeGrainEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nomGrain"), nom)));

        if(typeDeGrainsRepository.exists(spec))
            throw new AlreadyExistsException("Le grain existe déjà");

        TypeDeGrainEntity entity = new TypeDeGrainEntity();
        entity.setNomGrain(nom);
        typeDeGrainsRepository.save(entity);
    }

    /**
     * Récupère tous les types de grains présents dans le référentiel.
     *
     * Cette méthode interroge le référentiel des types de grains pour obtenir tous les types de grains enregistrés,
     * puis les retourne dans un ensemble (Set) de TypeDeGrainEntity.
     *
     * @return Un ensemble (Set) de TypeDeGrainEntity représentant tous les types de grains enregistrés.
     */
    @Override
    public Set<TypeDeGrainEntity> getAllGrains() {
        return typeDeGrainsRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Récupère un type de grain spécifié par son ID depuis le référentiel.
     *
     * Cette méthode prend l'ID d'un type de grain en entrée, recherche ce type de grain dans le référentiel des types de grains,
     * puis le retourne s'il est trouvé. Si le type de grain n'est pas trouvé, elle lève une exception NotFoundException.
     *
     * @param id L'ID du type de grain à récupérer.
     * @return Le type de grain (TypeDeGrainEntity) correspondant à l'ID spécifié.
     * @throws NotFoundException Si le type de grain n'est pas trouvé dans le référentiel.
     */
    @Override
    public TypeDeGrainEntity getOneGrain(Long id) {
        return typeDeGrainsRepository.findById(id).orElseThrow(()-> new NotFoundException("Grain not found"));
    }
}
