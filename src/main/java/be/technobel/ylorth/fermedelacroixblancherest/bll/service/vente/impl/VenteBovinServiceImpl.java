package be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.impl;

import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovin;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.vente.VenteBovinEntity;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.vente.VenteBovinForm;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.BovinEngraissementRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.vente.VenteBovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.bll.service.vente.VenteBovinService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenteBovinServiceImpl implements VenteBovinService {

    private final VenteBovinRepository venteBovinRepository;
    private final BovinEngraissementRepository bovinEngraissementRepository;

    public VenteBovinServiceImpl(VenteBovinRepository venteBovinRepository,
                                 BovinEngraissementRepository bovinEngraissementRepository) {
        this.venteBovinRepository = venteBovinRepository;
        this.bovinEngraissementRepository = bovinEngraissementRepository;
    }

    /**
     * Récupère la liste de toutes les ventes de bovins enregistrées dans le système.
     *
     * Cette méthode permet de récupérer la liste de toutes les ventes de bovins enregistrées dans le système. Elle renvoie une liste (List) de VenteBovinEntity, représentant chaque vente de bovin enregistrée.
     *
     * @return Une liste (List) contenant toutes les ventes de bovins enregistrées dans le système.
     */
    @Override
    public List<VenteBovinEntity> getAll() {
        return venteBovinRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    /**
     * Récupère une vente de bovin spécifique par son identifiant.
     *
     * Cette méthode permet de récupérer une vente de bovin spécifique à partir de son identifiant unique. Si la vente de bovin avec l'identifiant donné n'est pas trouvée, elle renvoie `null`.
     *
     * @param id L'identifiant unique de la vente de bovin à récupérer.
     * @return La vente de bovin correspondant à l'identifiant donné, ou `null` si aucune vente n'est trouvée.
     * @see VenteBovinEntity
     * @see VenteBovinRepository#findById(Object)
     */
    @Override
    public VenteBovinEntity getOne(Long id) {
        if(venteBovinRepository.existsById(id))
            return venteBovinRepository.findById(id).orElseThrow(()-> new NotFoundException("VenteBovin not found"));
        else
            return null;
    }

    /**
     * Insère une nouvelle vente de bovin dans le système.
     *
     * Cette méthode permet d'insérer une nouvelle vente de bovin dans le système en utilisant les informations fournies dans le formulaire de vente (`VenteBovinForm`). Les détails de la vente, tels que la date, la quantité, le prix de revente et le prix coûtant, sont extraits du formulaire et enregistrés dans la base de données.
     *
     * @param form Le formulaire de vente contenant les informations sur la vente de bovin à insérer.
     */
    @Override
    public void insert(VenteBovinForm form) {
        if(form!= null){
            VenteBovinEntity entity = new VenteBovinEntity();

            entity.setBovinEngraissement(bovinEngraissementRepository.findByNumeroInscription(form.getNumeroIdentification()).get());
            entity.setDateDeVente(form.getDate());
            entity.setQuantite(form.getQtt());
            entity.setPrixRevente(form.getPrixRevente());
            entity.setPrixCoutant(form.getPrixCoutant());
            venteBovinRepository.save(entity);
        }

    }

    /**
     * Met à jour les informations d'une vente de bovin existante dans le système.
     *
     * Cette méthode permet de mettre à jour les informations d'une vente de bovin existante dans le système en utilisant les informations fournies dans le formulaire de vente (VenteBovinForm) et l'identifiant de la vente (id). Les détails de la vente, tels que la date, la quantité, le prix de revente et le prix coûtant, sont extraits du formulaire et mis à jour dans la base de données pour la vente correspondante.
     *
     * @param id L'identifiant de la vente de bovin à mettre à jour.
     * @param form Le formulaire de vente contenant les nouvelles informations sur la vente de bovin.
     * @throws NotFoundException Si la vente de bovin avec l'identifiant spécifié n'est pas trouvée dans la base de données.
     */
    @Override
    public void update(Long id, VenteBovinForm form) {
        if(form!= null){
            VenteBovinEntity entity = venteBovinRepository.findById(id).orElseThrow(()-> new NotFoundException("VenteBovin not found"));

            entity.setBovinEngraissement(bovinEngraissementRepository.findByNumeroInscription(form.getNumeroIdentification()).orElseThrow(()-> new NotFoundException("Bovin not found")));
            entity.setDateDeVente(form.getDate());
            entity.setQuantite(form.getQtt());
            entity.setPrixRevente(form.getPrixRevente());
            entity.setPrixCoutant(form.getPrixCoutant());
            venteBovinRepository.save(entity);
        }
    }

    /**
     * Supprime une vente de bovin du système en utilisant son identifiant.
     *
     * Cette méthode permet de supprimer une vente de bovin existante dans le système en utilisant l'identifiant de la vente (`id`). La vente correspondante sera supprimée de la base de données.
     *
     * @param id L'identifiant de la vente de bovin à supprimer.
     */
    @Override
    public void delete(Long id) {
        if(venteBovinRepository.existsById(id))
        venteBovinRepository.deleteById(id);
    }
}
