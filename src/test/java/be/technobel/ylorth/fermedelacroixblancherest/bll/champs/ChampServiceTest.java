package be.technobel.ylorth.fermedelacroixblancherest.bll.champs;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.impl.ChampServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.ChampEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.TypeDeGrainEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.ChampRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.TypeDeGrainsRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChampServiceTest {

    @Mock
    ChampRepository champRepository;

    @Mock
    CultureRepository cultureRepository;

    @Mock
    TypeDeGrainsRepository grainsRepository;

    @InjectMocks
    ChampServiceImpl champService;

    //----------------------- Champs ------------------------------

    @Test
    void testGetAll() {
        ChampEntity champ1 = new ChampEntity();
        ChampEntity champ2 = new ChampEntity();
        List<ChampEntity> expectedChamps = List.of(champ1, champ2);
        when(champRepository.findAll()).thenReturn(expectedChamps);

        Set<ChampEntity> actualChamps = champService.getAll();

        assertTrue(expectedChamps.containsAll(actualChamps));
        verify(champRepository, times(1)).findAll();
    }

    @Test
    void testGetChampWhenChampExists() {
        ChampEntity expectedChamp = new ChampEntity();
        Long id = 1L;
        when(champRepository.findById(id)).thenReturn(Optional.of(expectedChamp));

        ChampEntity actualChamp = champService.getChamp(id);

        assertEquals(expectedChamp, actualChamp);
        verify(champRepository, times(1)).findById(id);
    }

    @Test
    void testGetChampWhenChampDoesNotExist() {
        Long id = 1L;
        when(champRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.getChamp(id));
        verify(champRepository, times(1)).findById(id);
        assertEquals("Champ not found", exception.getMessage());
    }


    @Test
    void testInsertChampWhenChampDoesNotExist() {
        // Arrange
        ChampInsertForm form = new ChampInsertForm("Test", 1234, "ha");
        when(champRepository.exists(any(Specification.class))).thenReturn(false);

        // Act
        champService.insertChamp(form);

        // Assert
        verify(champRepository, times(1)).exists(any(Specification.class));
        verify(champRepository, times(1)).save(any(ChampEntity.class));
    }

    @Test
    void testInsertChampWhenChampExists() {
        // Arrange
        ChampInsertForm form = new ChampInsertForm("Test", 1234, "ha");
        when(champRepository.exists(any(Specification.class))).thenReturn(true);

        // Act & Assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> champService.insertChamp(form));
        verify(champRepository, times(1)).exists(any(Specification.class));
        assertEquals("Champ déjà existant", exception.getMessage());
    }

    @Test
    void testInsertChampWhenFormIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> champService.insertChamp(null));
        assertEquals("Form ne peut être null", exception.getMessage());
    }

    @Test
    void testUpdateChampWhenFormIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> champService.updateChamp(1L, null));
        verify(champRepository, never()).findById(any());
        assertEquals("Form ne peut être null", exception.getMessage());
    }

    @Test
    void testUpdateChampWhenChampDoesNotExist() {
        // Arrange
        ChampUpdateForm form = new ChampUpdateForm("Test", 1234, LocalDate.now());
        when(champRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.updateChamp(1L, form));
        verify(champRepository, times(1)).findById(1L);
        assertEquals("Champ not found", exception.getMessage());
    }

    @Test
    void testUpdateChampWhenChampExists() {
        // Arrange
        ChampUpdateForm form = new ChampUpdateForm("Test", 1234, LocalDate.now());
        ChampEntity expectedChamp = new ChampEntity();
        when(champRepository.findById(1L)).thenReturn(Optional.of(expectedChamp));

        // Act
        champService.updateChamp(1L, form);

        // Assert
        verify(champRepository, times(1)).findById(1L);
        verify(champRepository, times(1)).save(expectedChamp);
    }

    //----------------------- Culture ------------------------------

    @Test
    void testInsertCulture() {
        // Arrange
        CultureForm form = new CultureForm(
                1L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                1L
        );

        ChampEntity champ = new ChampEntity();
        TypeDeGrainEntity grain = new TypeDeGrainEntity();
        // set grain fields if any

        when(champRepository.findById(any(Long.class))).thenReturn(Optional.of(champ));
        when(grainsRepository.findById(any(Long.class))).thenReturn(Optional.of(grain));

        // Act
        champService.insertCulture(form);

        // Assert
        verify(cultureRepository, times(1)).save(any(CultureEntity.class));
    }

    @Test
    void testInsertCultureWhenChampNotExist() {
        // Arrange
        CultureForm form = new CultureForm(
                -99L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                1L
        );
        when(champRepository.findById(any())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.insertCulture(form));
        verify(cultureRepository, never()).save(any());
        assertEquals("Champ not found", exception.getMessage());
    }

    @Test
    void testInsertCultureWhenTypeDeGrainsNotExist() {
        // Arrange
        CultureForm form = new CultureForm(
                1L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                -99L
        );
        when(champRepository.findById(any())).thenReturn(Optional.of(new ChampEntity()));
        when(grainsRepository.findById(any())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.insertCulture(form));
        verify(cultureRepository, never()).save(any());
        assertEquals("Grain not found", exception.getMessage());
    }

    @Test
    void testInsertCultureWhenFormIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> champService.insertCulture(null));
        verify(cultureRepository, never()).save(any());
        assertEquals("Form ne peut être null", exception.getMessage());
    }

    @Test
    void testGetCultureWhenCultureExists() {
        // Arrange
        CultureEntity expectedCulture = new CultureEntity();
        when(cultureRepository.findById(anyLong())).thenReturn(Optional.of(expectedCulture));

        // Act
        CultureEntity actualCulture = champService.getCulture(1L);

        // Assert
        assertEquals(expectedCulture, actualCulture);
        verify(cultureRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetCultureWhenCultureDoesNotExist() {

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.getCulture(-99L));

        assertEquals("Culture not found", exception.getMessage());
    }

    @Test
    void testUpdateCulture() {
        // Arrange
        CultureForm form = new CultureForm(
                1L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                -99L
        );
        ChampEntity champ = new ChampEntity();
        TypeDeGrainEntity grain = new TypeDeGrainEntity();
        CultureEntity culture = new CultureEntity();

        when(champRepository.findById(any(Long.class))).thenReturn(Optional.of(champ));
        when(grainsRepository.findById(any(Long.class))).thenReturn(Optional.of(grain));
        when(cultureRepository.findById(any(Long.class))).thenReturn(Optional.of(culture));

        // Act
        champService.updateCulture(1L, form);

        // Assert
        verify(cultureRepository, times(1)).save(any(CultureEntity.class));
    }

    @Test
    void testUpdateCultureWhenCultureDoesNotExist() {
        // Arrange
        CultureForm form = new CultureForm(
                1L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                1L
        );

        when(cultureRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.updateCulture(1L, form));
        verify(cultureRepository, never()).save(any());
        assertEquals("Culture not found", exception.getMessage());
    }

    @Test
    void testUpdateCultureWhenChampDoesNotExist() {
        // Arrange
        CultureForm form = new CultureForm(
                -99L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                1L
        );

        when(cultureRepository.findById(anyLong())).thenReturn(Optional.of(new CultureEntity()));
        when(champRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.updateCulture(1L, form));
        verify(cultureRepository, never()).save(any());
        assertEquals("Champ not found", exception.getMessage());
    }

    @Test
    void testUpdateCultureWhenGrainDoesNotExist() {
        // Arrange
        CultureForm form = new CultureForm(
                1L,
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1),
                1111,
                "",
                -99L
        );

        when(cultureRepository.findById(anyLong())).thenReturn(Optional.of(new CultureEntity()));
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(new ChampEntity()));
        when(grainsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.updateCulture(1L, form));
        verify(cultureRepository, never()).save(any());
        assertEquals("Grain not found", exception.getMessage());
    }

    @Test
    void testUpdateCultureWhenFormIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> champService.updateCulture(1L, null));
        verify(cultureRepository, never()).save(any());
        assertEquals("Form ne peut être null", exception.getMessage());
    }

    @Test
    void testGetHistoriqueWhenCulturesExist() {
        // Arrange
        Long id = 1L;
        List<CultureEntity> cultures = new ArrayList<>();
        cultures.add(new CultureEntity());

        when(cultureRepository.findAll(any(Specification.class))).thenReturn(cultures);

        // Act
        Set<CultureEntity> actualCultures = champService.getHistorique(id);

        // Assert
        assertTrue(cultures.containsAll(actualCultures));
        verify(cultureRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void testGetHistoriqueWhenNoCultures() {
        // Arrange

        when(cultureRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        // Act
        Set<CultureEntity> actualCultures = champService.getHistorique(1L);

        // Assert
        assertTrue(actualCultures.isEmpty());
        verify(cultureRepository, times(1)).findAll(any(Specification.class));
    }

    //----------------------- Grains ------------------------------

    @Test
    void testUpdateGrain() {
        String nom = "nom1";
        TypeDeGrainEntity grain = new TypeDeGrainEntity();

        when(grainsRepository.findById(any(Long.class))).thenReturn(Optional.of(grain));

        // Act
        champService.updateGrain(1L, nom);

        // Assert
        verify(grainsRepository, times(1)).save(any(TypeDeGrainEntity.class));
    }

    @Test
    void testUpdateGrainWhenGrainDoesNotExist() {
        String nom = "nom1";
        // Arrange
        when(grainsRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.updateGrain(1L, nom));
        verify(grainsRepository, never()).save(any());
        assertEquals("Grain not found", exception.getMessage());
    }

    @Test
    void testUpdateGrainWhenGrainExists() {
        String nom = "nom1";
        // Arrange
        when(grainsRepository.exists(any(Specification.class))).thenReturn(true);

        // Act & Assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> champService.updateGrain(1L, nom));
        verify(grainsRepository, never()).save(any());
        assertEquals("Grain déjà existant", exception.getMessage());
    }

    @Test
    void testUpdateGrainWhenNomIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> champService.updateGrain(1L, null));
        verify(grainsRepository, never()).save(any());
    }

    @Test
    void testInsertGrain() {
        // Arrange
        when(grainsRepository.exists(any(Specification.class))).thenReturn(false);

        // Act
        champService.insertGrain("nom");

        // Assert
        verify(grainsRepository, times(1)).save(any(TypeDeGrainEntity.class));
    }

    @Test
    void testInsertGrainWhenGrainExists() {
        // Arrange
        when(grainsRepository.exists(any(Specification.class))).thenReturn(true);

        // Act & Assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> champService.insertGrain("nom"));
        verify(grainsRepository, never()).save(any());
        assertEquals("Grain déjà existant", exception.getMessage());
    }

    @Test
    void testGetAllGrainsWhenGrainsExist() {
        // Arrange
        List<TypeDeGrainEntity> grains = new ArrayList<>();
        grains.add(new TypeDeGrainEntity());

        when(grainsRepository.findAll()).thenReturn(grains);

        // Act
        Set<TypeDeGrainEntity> actualGrains = champService.getAllGrains();

        // Assert
        assertTrue(grains.containsAll(actualGrains));
    }

    @Test
    void testGetAllGrainsWhenNoGrains() {
        // Arrange
        when(grainsRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Set<TypeDeGrainEntity> actualGrains = champService.getAllGrains();

        // Assert
        assertTrue(actualGrains.isEmpty());
    }

    @Test
    void testGetOneGrainWhenGrainExists() {
        // Arrange
        Long id = 1L;
        TypeDeGrainEntity grain = new TypeDeGrainEntity();
        // Set properties on grain if needed

        when(grainsRepository.findById(id)).thenReturn(Optional.of(grain));

        // Act
        TypeDeGrainEntity actualGrain = champService.getOneGrain(id);

        // Assert
        assertEquals(grain, actualGrain);
    }

    @Test
    void testGetOneGrainWhenNoGrain() {
        // Arrange
        Long id = 1L;

        when(grainsRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> champService.getOneGrain(id));
        assertEquals("Grain not found", exception.getMessage());
    }
}
