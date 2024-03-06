package be.technobel.ylorth.fermedelacroixblancherest.bll.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl.RaceServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.RaceEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.RaceRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.RaceForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {

    @InjectMocks
    private RaceServiceImpl raceService;

    @Mock
    private RaceRepository raceRepository;

    @Test
    public void testGetAll() {
        List<RaceEntity> raceList = new ArrayList<>();
        raceList.add(new RaceEntity());
        raceList.add(new RaceEntity());
        when(raceRepository.findAll()).thenReturn(raceList);

        Set<RaceEntity> result = raceService.getAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testGetOne_OK() {
        RaceEntity raceEntity = new RaceEntity();
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(raceEntity));

        RaceEntity result = raceService.getOne(1L);

        Assertions.assertEquals(raceEntity, result);
    }

    @Test
    public void testGetOne_NotFound() {

        when(raceRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, ()->raceService.getOne(-99L));
        Assertions.assertEquals("Race not found", exception.getMessage());
    }

    @Test
    public void testInsert_OK() {
        RaceForm form = new RaceForm("Test");

        raceService.insert(form.nom());

        Mockito.verify(raceRepository, Mockito.times(1)).save(Mockito.any(RaceEntity.class));
    }

    @Test
    public void testInsert_raceAlreadyExist() {
        RaceForm form = new RaceForm("Test");

        when(raceRepository.exists(any(Specification.class))).thenReturn(true);
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, ()-> raceService.insert(form.nom()));

        assertEquals("Race existe déjà", exception.getMessage());
    }

    @Test
    public void testUpdate_OK() {
        RaceForm form = new RaceForm("Test");
        when(raceRepository.exists(any(Specification.class))).thenReturn(false);
        when(raceRepository.existsById(anyLong())).thenReturn(true);
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(new RaceEntity()));

        raceService.update(1L, form.nom());

        Mockito.verify(raceRepository, Mockito.times(1)).save(Mockito.any(RaceEntity.class));
    }

    @Test
    public void testUpdate_raceAlreadyExist() {
        RaceForm form = new RaceForm("Test");

        when(raceRepository.exists(any(Specification.class))).thenReturn(true);
        when(raceRepository.findOne(any(Specification.class))).thenReturn(Optional.of(new RaceEntity()));

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, ()-> raceService.update(1L, form.nom()));

        assertEquals("Race déjà existante", exception.getMessage());
    }

    @Test
    public void testUpdate_notFound() {
        RaceForm form = new RaceForm("Test");
        when(raceRepository.exists(any(Specification.class))).thenReturn(false);
        when(raceRepository.existsById(anyLong())).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, ()-> raceService.update(-99L, form.nom()));

        assertEquals("Race not found", exception.getMessage());
    }

}
