package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.MelangeForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.MelangeRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.MelangeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MelangeServiceImpl implements MelangeService {
    private final MelangeRepository melangeRepository;

    public MelangeServiceImpl(MelangeRepository melangeRepository) {
        this.melangeRepository = melangeRepository;
    }

    /**
     * Récupère l'ensemble des enregistrements de mélanges présents dans le référentiel.
     *
     * Cette méthode interroge le référentiel des mélanges pour obtenir tous les enregistrements,
     * puis les retourne dans un ensemble (Set) de MelangeEntity.
     *
     * @return Un ensemble (Set) de MelangeEntity représentant tous les enregistrements de mélanges enregistrés.
     */
    @Override
    public Set<MelangeEntity> getAll() {
        return new HashSet<>(melangeRepository.findAll());
    }

    /**
     * Récupère un enregistrement de mélange spécifié par son ID depuis le référentiel.
     *
     * Cette méthode prend l'ID d'un enregistrement de mélange en entrée, recherche cet enregistrement dans le référentiel
     * de mélanges, puis le retourne s'il est trouvé. Si l'enregistrement n'est pas trouvé, elle lève une exception
     * NotFoundException.
     *
     * @param id L'ID de l'enregistrement de mélange à récupérer.
     * @return L'enregistrement de mélange (MelangeEntity) correspondant à l'ID spécifié.
     * @throws NotFoundException Si l'enregistrement de mélange n'est pas trouvé dans le référentiel.
     */
    @Override
    public MelangeEntity getOne(Long id) {
        return melangeRepository.findById(id).orElseThrow(()-> new NotFoundException("Mélange not found"));
    }

    /**
     * Insère un nouvel enregistrement de mélange dans le référentiel en utilisant les informations fournies dans le formulaire.
     *
     * Cette méthode prend un formulaire (MelangeForm) en entrée, vérifie que le formulaire n'est pas null,
     * vérifie également que le nom du mélange n'existe pas déjà dans le référentiel, puis crée un nouvel enregistrement
     * de mélange en utilisant les informations fournies dans le formulaire.
     *
     * @param form Le formulaire contenant les informations pour créer un nouvel enregistrement de mélange.
     * @throws IllegalArgumentException Si le formulaire est null.
     * @throws AlreadyExistsException Si le nom du mélange spécifié existe déjà dans le référentiel.
     */
    @Override
    public void insert(MelangeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");

        Specification<MelangeEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nomMelange"), form.nomMelange())));

        if(melangeRepository.exists(spec))
            throw new AlreadyExistsException("Mélange existe déjà");

        MelangeEntity entity =  new MelangeEntity();
        entity.setNomMelange(form.nomMelange());
        entity.setDescription(form.description());
        melangeRepository.save(entity);
    }

    /**
     * Met à jour un enregistrement de mélange spécifié par son ID en utilisant les données fournies dans le formulaire.
     *
     * Cette méthode prend l'ID d'un enregistrement de mélange en entrée, ainsi qu'un formulaire (MelangeForm) contenant
     * les nouvelles informations pour le mélange. Elle vérifie que le formulaire n'est pas null, s'assure que le nom du
     * mélange spécifié n'existe pas déjà dans le référentiel (sauf s'il s'agit du même mélange), puis met à jour les
     * informations du mélange en conséquence.
     *
     * @param id L'ID de l'enregistrement de mélange à mettre à jour.
     * @param form Le formulaire contenant les nouvelles informations pour le mélange.
     * @throws IllegalArgumentException Si le formulaire est null.
     * @throws AlreadyExistsException Si le nom du mélange spécifié existe déjà dans le référentiel (sauf pour le même mélange).
     */
    @Override
    public void update(Long id, MelangeForm form) {
        if(form == null)
            throw new IllegalArgumentException("form ne peut être null");

        Specification<MelangeEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nomMelange"), form.nomMelange())));

        if(melangeRepository.exists(spec)&& melangeRepository.findOne(spec).orElseThrow().getId()!=id)
            throw new AlreadyExistsException("Mélange déjà existant");
        
        if(!melangeRepository.existsById(id))
            throw new NotFoundException("Mélange not found");

        MelangeEntity entity =  new MelangeEntity();
        entity.setNomMelange(form.nomMelange());
        entity.setDescription(form.description());
        entity.setId(id);
        melangeRepository.save(entity);
    }

}
