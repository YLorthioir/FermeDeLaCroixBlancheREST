package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.impl;

import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFauche;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteFaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteFaucheForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.FaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vente.VenteFaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.VenteFaucheService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenteFaucheServiceImpl implements VenteFaucheService {

    private final VenteFaucheRepository venteFaucheRepository;
    private final FaucheRepository faucheRepository;

    public VenteFaucheServiceImpl(VenteFaucheRepository venteFaucheRepository,
                                  FaucheRepository faucheRepository) {
        this.venteFaucheRepository = venteFaucheRepository;
        this.faucheRepository = faucheRepository;
    }

    /**
     * Récupère la liste de toutes les ventes de fauche dans le système.
     *
     * Cette méthode permet de récupérer la liste de toutes les ventes de fauche enregistrées dans le système. Les ventes de fauche sont collectées depuis la base de données et renvoyées sous forme de liste d'entités.
     *
     * @return La liste de toutes les ventes de fauche dans le système.
     */
    @Override
    public List<VenteFaucheEntity> getAll() {
        return venteFaucheRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    /**
     * Récupère une vente de fauche spécifique par son identifiant.
     *
     * Cette méthode permet de récupérer une vente de fauche spécifique enregistrée dans le système en utilisant son identifiant unique. Si la vente de fauche avec l'identifiant spécifié est trouvée dans la base de données, elle est renvoyée sous forme d'entité. Sinon, une exception `NotFoundException` est levée.
     *
     * @param id L'identifiant unique de la vente de fauche à récupérer.
     * @return La vente de fauche spécifique correspondant à l'identifiant.
     * @throws NotFoundException Si la vente de fauche avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public VenteFaucheEntity getOne(Long id) {
        return venteFaucheRepository.findById(id).orElseThrow(()-> new NotFoundException("VenteFauche not found"));
    }

    /**
     * Insère une nouvelle vente de fauche dans le système.
     *
     * Cette méthode permet d'insérer une nouvelle vente de fauche dans le système en utilisant les informations fournies dans le formulaire `VenteFaucheForm`. Si le formulaire est valide et contient toutes les informations nécessaires, une nouvelle vente de fauche est créée et enregistrée dans la base de données.
     *
     * @param form Le formulaire contenant les informations de la vente de fauche à insérer.
     * @throws NotFoundException Si la fauche avec l'identifiant spécifié dans le formulaire n'est pas trouvée.
     */
    @Override
    public void insert(VenteFaucheForm form) {
        if(form!=null){
            VenteFaucheEntity entity = new VenteFaucheEntity();
            entity.setFauche(faucheRepository.findById(form.faucheId()).orElseThrow(()-> new NotFoundException("Fauche not found")));
            entity.setQuantite(form.qtt());
            entity.setDateDeVente(form.date());
            entity.setPrixCoutant(form.prixCoutant());
            entity.setPrixRevente(form.prixRevente());
            venteFaucheRepository.save(entity);
        }

    }

    /**
     * Met à jour une vente de fauche existante dans le système.
     *
     * Cette méthode permet de mettre à jour une vente de fauche existante dans le système en utilisant les informations fournies dans le formulaire (VenteFaucheForm). Si le formulaire est valide et contient toutes les informations nécessaires, la vente de fauche correspondante est mise à jour dans la base de données.
     *
     * @param id L'identifiant de la vente de fauche à mettre à jour.
     * @param form Le formulaire contenant les nouvelles informations de la vente de fauche.
     * @throws NotFoundException Si la vente de fauche avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public void update(Long id, VenteFaucheForm form) {
        if(form!=null){
            VenteFaucheEntity entity = venteFaucheRepository.findById(id).orElseThrow(()-> new NotFoundException("VenteFauche not found"));
            entity.setFauche(faucheRepository.findById(form.faucheId()).orElseThrow(()-> new NotFoundException("Fauche not found")));
            entity.setQuantite(form.qtt());
            entity.setDateDeVente(form.date());
            entity.setPrixCoutant(form.prixCoutant());
            entity.setPrixRevente(form.prixRevente());
            venteFaucheRepository.save(entity);
        }
    }

    /**
     * Supprime une vente de fauche du système.
     *
     * Cette méthode permet de supprimer une vente de fauche existante du système en utilisant son identifiant. Si une vente de fauche avec l'identifiant spécifié existe dans la base de données, elle sera supprimée.
     *
     * @param id L'identifiant de la vente de fauche à supprimer.
     */
    @Override
    public void delete(Long id) {
        if(venteFaucheRepository.existsById(id))
        venteFaucheRepository.deleteById(id);
    }
}
