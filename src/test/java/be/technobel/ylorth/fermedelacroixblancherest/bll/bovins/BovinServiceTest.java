package be.technobel.ylorth.fermedelacroixblancherest.bll.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl.BovinServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.*;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BovinServiceTest {

    @InjectMocks
    private BovinServiceImpl bovinService;
    
    @Mock
    private BovinRepository bovinRepository;
    
    @Mock
    private RaceRepository raceRepository;
    
    @Mock
    private FemelleReproductionRepository femelleReproductionRepository;
    
    @Mock
    private BovinEngraissementRepository bovinEngraissementRepository;
    
    @Mock
    private ChampRepository champRepository;
    
    @Mock
    private MelangeRepository melangeRepository;
    
    /*
        getAllNI():
            - Bovin n'existent pas
            - Bovin existent
     */
    @Test
    public void testGetAllNI_WhenNoBovinEntitiesExist() {
        when(bovinRepository.findAll()).thenReturn(new ArrayList<>());

        Set<String> result = bovinService.getAllNI();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllNI_WhenBovinEntitiesExist() {
        BovinEntity bovinEntity1 = new BovinEntity();
        bovinEntity1.setNumeroInscription("BE1234123412");

        BovinEntity bovinEntity2 = new BovinEntity();
        bovinEntity2.setNumeroInscription("BE1234123413");

        when(bovinRepository.findAll()).thenReturn(List.of(bovinEntity1, bovinEntity2));

        Set<String> result = bovinService.getAllNI();

        assertEquals(2, result.size());
        assertTrue(result.contains("BE1234123412"));
        assertTrue(result.contains("BE1234123413"));
    }

    /*
    getOne():
        - Bovin trouvé mais pas d'enfants en césarienne
        - Bovin trouvé avec enfants en césarienne
        - Bovin trouvé mais pas d'enfants
        - Bovin non trouvé
    */
    @Test
    public void testGetOne_WhenBovinExistsWithNoCesarienneChildren() {
        BovinEntity bovinEntity = new BovinEntity();
        bovinEntity.setNumeroInscription("BE1234123412");
        bovinEntity.setSexe('F');
        
        BovinEntity child = new BovinEntity();
        child.setNeCesarienne(false);

        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(bovinEntity));
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(List.of(child));
        
        Bovin result = bovinService.getOne("BE1234123412");

        assertEquals("BE1234123412", result.numeroInscription());
        assertEquals(0, result.nbCesarienne());
    }

    @Test
    public void testGetOne_WhenBovinExistsWithCesarienneChildren() {
        BovinEntity bovinEntity = new BovinEntity();
        bovinEntity.setNumeroInscription("BE1234123412");
        bovinEntity.setSexe('F');

        BovinEntity child = new BovinEntity();
        child.setNeCesarienne(true);
        BovinEntity child2 = new BovinEntity();
        child2.setNeCesarienne(true);

        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(bovinEntity));
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(List.of(child, child2));

        Bovin result = bovinService.getOne("BE1234123412");

        assertEquals("BE1234123412", result.numeroInscription());
        assertEquals(2, result.nbCesarienne());
    }

    @Test
    public void testGetOne_WhenBovinNotFound() {
        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bovinService.getOne("PasTestette"));

        assertEquals("bovin not found", exception.getMessage());
    }

    /*
        getChildren():
            - Bovin trouvé mais pas d'enfants
            - Bovin trouvé avec enfants
            - Bovin pas trouvé
     */
    @Test
    public void testGetChildren_WhenBovinExistsButHasNoChildren() {
        BovinEntity bovinEntity = new BovinEntity();
        bovinEntity.setNumeroInscription("BE1234123412");
        bovinEntity.setSexe('F');

        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(bovinEntity));
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        Set<BovinEntity> result = bovinService.getChildren("BE1234123412");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetChildren_WhenBovinExistsAndHasChildren() {
        BovinEntity bovinEntity = new BovinEntity();
        bovinEntity.setNumeroInscription("BE1234123412");
        bovinEntity.setSexe('M');

        BovinEntity child = new BovinEntity();
        child.setPereNI("BE1234123412");

        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(bovinEntity));
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(List.of(child));

        Set<BovinEntity> result = bovinService.getChildren("BE1234123412");

        assertEquals(1, result.size());
        assertEquals("BE1234123412", result.iterator().next().getPereNI());
    }

    @Test
    public void testGetChildren_WhenBovinNotFound() {
        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bovinService.getChildren("BE1234123412"));

        assertEquals("BovinEntity not found", exception.getMessage());
    }

    /*
        createBovin():
            - OK
            - Numéro identifications déjà existant
            - Race non trouvée
            - Formulaire null
     */
    @Test
    public void testCreateBovin_OK() {
        BovinInsertForm form = new BovinInsertForm("BE1234123412", "M", LocalDate.now(), 200, false, 1L, "BE1234123413", "BE1234123414" );

        RaceEntity raceEntity = new RaceEntity();

        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(raceEntity));
        
        bovinService.createBovin(form);

        verify(bovinRepository, times(1)).save(any(BovinEntity.class));
    }

    @Test
    public void testCreateBovin_WhenBovinWithSameNIExists() {
        BovinInsertForm form = new BovinInsertForm("BE1234123412", "M", LocalDate.now(), 200, false, 1L, "Testeau", "Testette" );

        when(bovinRepository.exists(any(Specification.class))).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> bovinService.createBovin(form));

        assertEquals("Le numéro d'identification du BovinEntity existe déjà", exception.getMessage());
    }

    @Test
    public void testCreateBovin_WhenRaceNotFound() {
        BovinInsertForm form = new BovinInsertForm("BE1234123412", "M", LocalDate.now(), 200, false, -99L, "BE1234123412", "BE1234123412" );

        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);
        when(raceRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bovinService.createBovin(form));

        assertEquals("race not found", exception.getMessage());
    }

    @Test
    public void testCreateBovin_WhenFormIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bovinService.createBovin(null));

        assertEquals("le formulaire ne peut être null", exception.getMessage());
    }

    /*
    updateBovin():
        - Reproduction OK
        - Engraissement OK
        - Bovin OK
        - Numéro identifications déjà existant
        - Race non trouvée
        - Champ non trouvé
        - Formulaire null
    */
    @Test
    public void testUpdateBovin_WhenFemelleReproductionExists() {
        BovinUpdateForm form = new BovinUpdateForm("BE1234123412", "M","NomDeTasty", LocalDate.now(), 200, false, -99L, "BE1234123412", "BE1234123412", 1L, true, LocalDate.now(), "", null, 0, 1000, 800, LocalDate.now(), 1L );
        ChampEntity champ = new ChampEntity();
        RaceEntity race = new RaceEntity();
        
        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);
        when(femelleReproductionRepository.existsById(anyLong())).thenReturn(true);
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(champ));
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(race));

        bovinService.updateBovin(1L, form);

        verify(femelleReproductionRepository, times(1)).save(any(FemelleReproductionEntity.class));
    }

    @Test
    public void testUpdateBovin_WhenBovinEngraissementExists() {
        BovinUpdateForm form = new BovinUpdateForm("BE1234123412", "M","NomDeTasty", LocalDate.now(), 200, false, -99L, "BE1234123412", "BE1234123412", 1L, true, LocalDate.now(), "", null, 0, 1000, 800, LocalDate.now(), 1L );
        ChampEntity champ = new ChampEntity();
        RaceEntity race = new RaceEntity();
        MelangeEntity melange = new MelangeEntity();
        
        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);
        when(femelleReproductionRepository.existsById(anyLong())).thenReturn(false);
        when(bovinEngraissementRepository.existsById(anyLong())).thenReturn(true);
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(champ));
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(race));
        when(melangeRepository.findById(anyLong())).thenReturn(Optional.of(melange));

        bovinService.updateBovin(1L, form);

        verify(bovinEngraissementRepository, times(1)).save(any(BovinEngraissementEntity.class));
    }

    @Test
    public void testUpdateBovin_Bovin() {
        BovinUpdateForm form = new BovinUpdateForm("BE1234123412", "M","NomDeTasty", LocalDate.now(), 200, false, -99L, "BE1234123412", "BE1234123412", 1L, true, LocalDate.now(), "", null, 0, 1000, 800, LocalDate.now(), 1L );
        ChampEntity champ = new ChampEntity();
        RaceEntity race = new RaceEntity();
        
        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);
        when(femelleReproductionRepository.existsById(anyLong())).thenReturn(false);
        when(bovinEngraissementRepository.existsById(anyLong())).thenReturn(false);
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(champ));
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(race));

        bovinService.updateBovin(1L, form);

        verify(bovinRepository, times(1)).save(any(BovinEntity.class));
    }

    @Test
    public void testUpdateBovin_WhenBovinWithSameNIExists() {
        BovinUpdateForm form = new BovinUpdateForm("BE1234123412", "M","NomDeTasty", LocalDate.now(), 200, false, 1L, "BE1234123412", "BE1234123412", 1L, true, LocalDate.now(), "", null, 0, 1000, 800, LocalDate.now(), 1L );

        BovinEntity existingBovin = new BovinEntity();
        existingBovin.setId(2L);

        when(bovinRepository.exists(any(Specification.class))).thenReturn(true);
        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(existingBovin));

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> bovinService.updateBovin(1L, form));

        assertEquals("BovinEntity déjà existant", exception.getMessage());
    }

    @Test
    public void testUpdateBovin_WhenRaceNotExists() {
        BovinUpdateForm form = new BovinUpdateForm("BE1234123412", "M","NomDeTasty", LocalDate.now(), 200, false, -99L, "BE1234123412", "BE1234123412", 1L, true, LocalDate.now(), "", null, 0, 1000, 800, LocalDate.now(), 1L );
        
        BovinEntity existingBovin = new BovinEntity();
        existingBovin.setId(2L);

        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bovinService.updateBovin(1L, form));

        assertEquals("Race not found", exception.getMessage());
    }

    @Test
    public void testUpdateBovin_WhenChampNotExists() {
        BovinUpdateForm form = new BovinUpdateForm("BE1234123412", "M","NomDeTasty", LocalDate.now(), 200, false, 1L, "BE1234123412", "BE1234123412", -99L, true, LocalDate.now(), "", null, 0, 1000, 800, LocalDate.now(), 1L );
        RaceEntity race = new RaceEntity();

        BovinEntity existingBovin = new BovinEntity();
        existingBovin.setId(2L);

        when(bovinRepository.exists(any(Specification.class))).thenReturn(false);
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(race));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bovinService.updateBovin(1L, form));

        assertEquals("Champ not found", exception.getMessage());
    }

    @Test
    public void testUpdateBovin_WhenFormIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bovinService.updateBovin(1L, null));

        assertEquals("le formulaire ne peut être null", exception.getMessage());
    }

    /*
    getInfosReproduction():
        - Bovin n'a pas eu de césarienne
        - Bovin a eu des césariennes
        - Bovin pas trouvé
    */
    @Test
    public void testGetInfosReproduction_WhenBovinExistsWithNoCesarienneChildren() {
        FemelleReproductionEntity entity = new FemelleReproductionEntity();
        entity.setId(1L);
        entity.setSexe('F');
        entity.setNumeroInscription("BE1234123412");
        entity.setDerniereInsemination(LocalDate.now());
        entity.setPerteGrossesse(0);
        entity.setMereNI("BE1234123413");
        
        BovinEntity bovin = new BovinEntity();
        bovin.setSexe('M');
        
        when(femelleReproductionRepository.existsById(anyLong())).thenReturn(true);
        when(femelleReproductionRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(bovin));
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(List.of());

        InfosReproduction result = bovinService.getInfosReproduction(1L);

        assertEquals(0, result.nbCesarienne());
    }

    @Test
    public void testGetInfosReproduction_WhenBovinExistsWithCesarienneChildren() {
        FemelleReproductionEntity entity = new FemelleReproductionEntity();
        entity.setId(1L);
        entity.setNumeroInscription("BE1234123412");

        BovinEntity bovin = new BovinEntity();
        bovin.setSexe('M');

        BovinEntity child = new BovinEntity();
        child.setNeCesarienne(true);

        BovinEntity child2 = new BovinEntity();
        child2.setNeCesarienne(true);

        when(femelleReproductionRepository.existsById(anyLong())).thenReturn(true);
        when(femelleReproductionRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(bovinRepository.findOne(any(Specification.class))).thenReturn(Optional.of(bovin));
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(List.of(child, child2));

        InfosReproduction result = bovinService.getInfosReproduction(1L);

        assertEquals(2, result.nbCesarienne());
    }

    @Test
    public void testGetInfosReproduction_WhenBovinDoesNotExist() {
        when(femelleReproductionRepository.existsById(anyLong())).thenReturn(false);

        InfosReproduction result = bovinService.getInfosReproduction(1L);

        assertNull(result);
    }

    /*
    getInfosEngraissement():
        - OK
        - Bovin pas trouvé
    */
    @Test
    public void testGetInfosEngraissement_WhenBovinExists() {
        BovinEngraissementEntity entity = new BovinEngraissementEntity();
        entity.setId(1L);
        entity.setPoidsSurPattes(123.45);
        entity.setPoidsCarcasse(67.89);
        entity.setDateEngraissement(LocalDate.now());

        MelangeEntity melangeEntity = new MelangeEntity();
        entity.setMelange(melangeEntity);

        when(bovinEngraissementRepository.existsById(anyLong())).thenReturn(true);
        when(bovinEngraissementRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        InfosEngraissement result = bovinService.getInfosEngraissement(1L);

        assertEquals(123.45, result.poidsSurPattes(), 0.001);
        assertEquals(67.89, result.poidsCarcasse(), 0.001);
        assertEquals(LocalDate.now(), result.dateEngraissement());
        assertNotNull(result.melange());
    }
    
    @Test
    public void testGetInfosEngraissement_WhenBovinDoesNotExist() {
        when(bovinEngraissementRepository.existsById(anyLong())).thenReturn(false);

        InfosEngraissement result = bovinService.getInfosEngraissement(1L);

        assertNull(result);
    }

    /*
    updateType():
        - Bovin
        - Bovin engraissement
        - Bovin reproduction
        - Finalité inconnue
        - Finalité null
    */
    @Test
    public void testUpdateType_WhenFinaliteIsBovin() {
        BovinUpdateTypeForm form = new BovinUpdateTypeForm("Bovin");

        bovinService.updateType(1L, form);

        verify(bovinRepository, times(1)).changeType(anyLong(), anyString());
    }

    @Test
    public void testUpdateType_WhenFinaliteIsFemelleReproduction() {
        BovinUpdateTypeForm form = new BovinUpdateTypeForm("FemelleReproduction");

        bovinService.updateType(1L, form);

        verify(bovinRepository, times(1)).changeType(anyLong(), anyString());
    }

    @Test
    public void testUpdateType_WhenFinaliteIsBovinEngraissement() {
        BovinUpdateTypeForm form = new BovinUpdateTypeForm("BovinEngraissement");

        bovinService.updateType(1L, form);

        verify(bovinRepository, times(1)).changeType(anyLong(), anyString());
    }

    @Test
    public void testUpdateType_WhenFinaliteIsOther() {
        BovinUpdateTypeForm form = new BovinUpdateTypeForm("Autre");

        bovinService.updateType(1L, form);

        verify(bovinRepository, times(0)).changeType(anyLong(), anyString());
    }

    @Test
    public void testUpdateType_WhenFinaliteIsNull() {
        BovinUpdateTypeForm form = new BovinUpdateTypeForm(null);

        bovinService.updateType(1L, form);

        verify(bovinRepository, times(0)).changeType(anyLong(), anyString());
    }

    /*
    getAllTaureaux():
        - Taureau exists
        - Taureau not found
    */
    @Test
    public void testGetAllTaureaux_WhenTaureauExist() {
        BovinEntity taureau1 = new BovinEntity();
        taureau1.setSexe('M');
        taureau1.setNom("Taureau1");

        BovinEntity taureau2 = new BovinEntity();
        taureau2.setSexe('M');
        taureau2.setNom("Taureau2");

        when(bovinRepository.findAll(any(Specification.class))).thenReturn(List.of(taureau1, taureau2));

        Set<BovinEntity> result = bovinService.getAllTaureaux();

        assertEquals(2, result.size());
        assertTrue(result.stream().map(BovinEntity::getNom).collect(Collectors.toSet()).containsAll(List.of("Taureau1", "Taureau2")));
    }

    @Test
    public void testGetAllTaureaux_WhenNoTaureauxExist() {
        when(bovinRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        Set<BovinEntity> result = bovinService.getAllTaureaux();

        assertTrue(result.isEmpty());
    }

    /*
    getAllEngraissement():
        - Bovin engraissement existe
        - Bovin engraissement not found
    */
    @Test
    public void testGetAllEngraissement_WhenEngraissementExists() {
        BovinEngraissementEntity entity1 = new BovinEngraissementEntity();
        entity1.setNumeroInscription("BE1234123412");

        BovinEngraissementEntity entity2 = new BovinEngraissementEntity();
        entity2.setNumeroInscription("BE1234123413");

        when(bovinEngraissementRepository.findAll()).thenReturn(List.of(entity1, entity2));

        Set<String> result = bovinService.getAllEngraissement();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of("BE1234123412", "BE1234123413")));
    }
    
    @Test
    public void testGetAllEngraissement_WhenNoEngraissementExist() {
        when(bovinEngraissementRepository.findAll()).thenReturn(new ArrayList<>());

        Set<String> result = bovinService.getAllEngraissement();

        assertTrue(result.isEmpty());
    }
}
