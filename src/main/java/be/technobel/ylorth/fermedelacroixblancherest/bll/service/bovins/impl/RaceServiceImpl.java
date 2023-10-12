package be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.RaceService;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.RaceRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;

    public RaceServiceImpl(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    /**
     * Récupère l'ensemble des enregistrements de races présents dans le référentiel.
     *
     * Cette méthode interroge le référentiel des races pour obtenir tous les enregistrements de races,
     * puis les retourne dans un ensemble (Set) de RaceEntity.
     *
     * @return Un ensemble (Set) de RaceEntity représentant tous les enregistrements de races enregistrés.
     */
    @Override
    public Set<RaceEntity> getAll() {
        return raceRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Insère une nouvelle race dans le référentiel en utilisant le nom de la race fourni.
     *
     * Cette méthode prend le nom d'une nouvelle race en entrée, vérifie que la race avec ce nom n'existe pas déjà
     * dans le référentiel, puis crée une nouvelle entrée de race en utilisant le nom spécifié.
     *
     * @param nom Le nom de la nouvelle race à insérer.
     * @throws AlreadyExistsException Si une race avec le même nom existe déjà dans le référentiel.
     */
    @Override
    public void insert(String nom) {
        Specification<RaceEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),nom)));

        if(raceRepository.exists(spec))
            throw new AlreadyExistsException("Race existe déjà");

        RaceEntity entity = new RaceEntity();
        entity.setNom(nom);
        raceRepository.save(entity);
    }

    /**
     * Met à jour le nom d'une race spécifiée par son ID en utilisant le nouveau nom fourni.
     *
     * Cette méthode prend l'ID d'une race en entrée, ainsi qu'un nouveau nom pour la race. Elle vérifie que le nouveau nom
     * n'est pas déjà utilisé par une autre race dans le référentiel (sauf pour la même race), puis met à jour le nom de la race
     * en conséquence.
     *
     * @param id L'ID de la race à mettre à jour.
     * @param nom Le nouveau nom pour la race.
     * @throws AlreadyExistsException Si le nouveau nom de la race est déjà utilisé par une autre race dans le référentiel (sauf pour la même race).
     */
    @Override
    public void update(Long id, String nom) {

        Specification<RaceEntity> spec = (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nom"),nom)));

        if(raceRepository.exists(spec) && raceRepository.findOne(spec).get().getId()!=id)
            throw new AlreadyExistsException("Race déjà existante");

        RaceEntity entity = raceRepository.findById(id).get();
        entity.setNom(nom);
        raceRepository.save(entity);
    }

    /**
     * Récupère une race spécifiée par son ID depuis le référentiel.
     *
     * Cette méthode prend l'ID d'une race en entrée, recherche cette race dans le référentiel des races,
     * puis la retourne si elle est trouvée. Si la race n'est pas trouvée, elle lève une exception
     * NotFoundException.
     *
     * @param id L'ID de la race à récupérer.
     * @return La race (RaceEntity) correspondant à l'ID spécifié.
     * @throws NotFoundException Si la race n'est pas trouvée dans le référentiel.
     */
    @Override
    public RaceEntity getOne(Long id) {
        return raceRepository.findById(id).orElseThrow(()-> new NotFoundException("Race not found"));
    }
}
