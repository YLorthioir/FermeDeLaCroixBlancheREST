package be.technobel.ylorth.fermedelacroixblancherest.bll.champs;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.champs.impl.FaucheServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.FaucheInsertException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.CultureEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.champs.FaucheEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.CultureRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.champs.FaucheRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheInsertForm;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.champs.FaucheUpdateForm;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FaucheServiceTest {
    
    @Mock
    FaucheRepository faucheRepository;

    @Mock
    CultureRepository cultureRepository;
    
    @InjectMocks
    FaucheServiceImpl faucheService;

    @Test
    void testGetOneWhenFaucheExists() {
        // Arrange
        FaucheEntity fauche = new FaucheEntity();
        // Set properties on fauche if needed

        when(faucheRepository.findById(anyLong())).thenReturn(Optional.of(fauche));

        // Act
        FaucheEntity actualFauche = faucheService.getOne(1L);

        // Assert
        assertEquals(fauche, actualFauche);
    }

    @Test
    void testGetOneWhenNoFauche() {
        // Arrange

        when(faucheRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
       NotFoundException exception = assertThrows(NotFoundException.class, () -> faucheService.getOne(-99L));
       assertEquals("Fauche not found", exception.getMessage());
    }

    @Test
    void testInsertWhenNoMatchingCulture() {
        FaucheInsertForm form = new FaucheInsertForm(1L,2023, LocalDate.now().minusDays(1),200);
        when(cultureRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> faucheService.insert(form));
        assertEquals("Culture not found", exception.getMessage());
    }

    @Test
    void testInsertWhenNoMatchingFaucheEntity() {
        // Arrange
        FaucheInsertForm form = new FaucheInsertForm(1L,LocalDate.now().getYear(), LocalDate.now().minusDays(1),200);
        CultureEntity culture = new CultureEntity();
        culture.setDateMiseEnCulture(LocalDate.now());
        when(cultureRepository.findAll(any(Specification.class))).thenReturn(List.of(culture));
        when(faucheRepository.exists(any(Specification.class))).thenReturn(false);
        when(cultureRepository.findById(anyLong())).thenReturn(Optional.of(culture));

        // Act
        faucheService.insert(form);

        // Assert
        verify(faucheRepository, times(1)).save(any(FaucheEntity.class));
    }

    @Test
    void testInsertWhenAllFauchesAreFilled() {
        // Arrange
        FaucheInsertForm form = new FaucheInsertForm(1L,2023, LocalDate.now().minusDays(1),200);
        CultureEntity culture = new CultureEntity();
        culture.setDateMiseEnCulture(LocalDate.now());
        FaucheEntity fauche = new FaucheEntity();
        fauche.setFauche1(LocalDate.now().minusDays(5));
        fauche.setFauche2(LocalDate.now().minusDays(4));
        fauche.setFauche3(LocalDate.now().minusDays(3));
        fauche.setFauche4(LocalDate.now().minusDays(2));

        when(cultureRepository.findAll(any(Specification.class))).thenReturn(List.of(culture));
        when(faucheRepository.exists(any(Specification.class))).thenReturn(true);
        when(faucheRepository.findOne(any(Specification.class))).thenReturn(Optional.of(fauche));

        // Act & Assert
        FaucheInsertException exception = assertThrows(FaucheInsertException.class, () -> faucheService.insert(form));
        verify(faucheRepository, never()).save(any(FaucheEntity.class));
        assertEquals("Nombre de fauches max atteintes", exception.getMessage());
    }

    @Test
    void testInsertWhenFormIsNull() {
        FaucheInsertForm form = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> faucheService.insert(form));
        assertEquals("form ne peut être null", exception.getMessage());
    }

    @Test
    void testUpdateInNormalCase() {
        FaucheUpdateForm form = new FaucheUpdateForm(
            1L,
            2023,
            LocalDate.now().minusDays(4),
            200,
            LocalDate.now().minusDays(3),
            200,
            LocalDate.now().minusDays(2),
            200,
            LocalDate.now().minusDays(1),
            200
        );
        when(faucheRepository.findById(anyLong())).thenReturn(Optional.of(new FaucheEntity()));
        when(cultureRepository.findById(form.cultureId())).thenReturn(Optional.of(new CultureEntity()));

        faucheService.update(1L, form);

        verify(faucheRepository, times(1)).save(any(FaucheEntity.class));
    }

    @Test
    void testUpdateWhenNoMatchingCulture() {
        FaucheUpdateForm form = new FaucheUpdateForm(
                -99L,
                2023,
                LocalDate.now().minusDays(4),
                200,
                LocalDate.now().minusDays(3),
                200,
                LocalDate.now().minusDays(2),
                200,
                LocalDate.now().minusDays(1),
                200
        );
        when(faucheRepository.findById(anyLong())).thenReturn(Optional.of(new FaucheEntity()));
        when(cultureRepository.findById(form.cultureId())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> faucheService.update(1L, form));
        assertEquals("Culture not found", exception.getMessage());
    }

    @Test
    void testUpdateWhenNoFaucheEntity() {
        FaucheUpdateForm form = new FaucheUpdateForm(
                1L,
                2023,
                LocalDate.now().minusDays(4),
                200,
                LocalDate.now().minusDays(3),
                200,
                LocalDate.now().minusDays(2),
                200,
                LocalDate.now().minusDays(1),
                200
        );        
        when(faucheRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> faucheService.update(-99L, form));
        assertEquals("Fauche not found", exception.getMessage());
    }

    @Test
    void testUpdateWhenFormIsNull() {
        Long id = 1L;
        FaucheUpdateForm form = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> faucheService.update(id, form));
        assertEquals("form ne peut être null", exception.getMessage());
    }

    @Test
    void testGetAllWhenFauchesExist() {
        // Arrange
        String nomChamp = "nomChamp";
        FaucheEntity fauche1 = new FaucheEntity();
        FaucheEntity fauche2 = new FaucheEntity();
        when(faucheRepository.findAll(any(Specification.class))).thenReturn(List.of(fauche1, fauche2));

        // Act
        Set<FaucheEntity> actualFauches = faucheService.getAll(nomChamp);

        // Assert
        assertEquals(2, actualFauches.size());
    }

    @Test
    void testGetAllWhenNoFauches() {
        // Arrange
        String nomChamp = "nomChamp";

        when(faucheRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        // Act
        Set<FaucheEntity> actualFauches = faucheService.getAll(nomChamp);

        // Assert
        assertTrue(actualFauches.isEmpty());
    }

    @Test
    void testGetAllByAnneeWhenFauchesExist() {
        // Arrange
        int annee = 2023;
        FaucheEntity fauche1 = new FaucheEntity();
        FaucheEntity fauche2 = new FaucheEntity();
        // Set properties on fauches if needed

        when(faucheRepository.findAll(any(Specification.class))).thenReturn(List.of(fauche1, fauche2));

        // Act
        Set<FaucheEntity> actualFauches = faucheService.getAll(annee);

        // Assert
        assertEquals(2,actualFauches.size());
    }

    @Test
    void testGetByAnneeAllWhenNoFauches() {
        // Arrange
        int annee = 2023;

        when(faucheRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        // Act
        Set<FaucheEntity> actualFauches = faucheService.getAll(annee);

        // Assert
        assertTrue(actualFauches.isEmpty());
    }

    @Test
    void testGetAllAnneeWhenFauchesExist() {
        // Arrange
        FaucheEntity fauche1 = new FaucheEntity();
        fauche1.setAnnee(2021);
        FaucheEntity fauche2 = new FaucheEntity();
        fauche2.setAnnee(2022);

        when(faucheRepository.findAll(any(Specification.class))).thenReturn(List.of(fauche1, fauche2));

        // Act
        Set<Integer> actualYears = faucheService.getAllAnnee();

        // Assert
        assertEquals(Set.of(2021, 2022), actualYears);
    }

    @Test
    void testGetAllAnneeWhenNoFauches() {
        // Arrange
        when(faucheRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        // Act
        Set<Integer> actualYears = faucheService.getAllAnnee();

        // Assert
        assertTrue(actualYears.isEmpty());
    }
}
