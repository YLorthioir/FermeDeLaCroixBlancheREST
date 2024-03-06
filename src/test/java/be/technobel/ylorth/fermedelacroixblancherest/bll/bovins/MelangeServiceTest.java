package be.technobel.ylorth.fermedelacroixblancherest.bll.bovins;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.bovins.impl.MelangeServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.dal.models.bovins.MelangeEntity;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.MelangeRepository;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.bovins.MelangeForm;
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

public class MelangeServiceTest {
    
    @InjectMocks
    private MelangeServiceImpl melangeService;
    
    @Mock
    private MelangeRepository melangeRepository;
    
    @Test
    public void testGetAll() {
        List<MelangeEntity> melangeList = new ArrayList<>();
        melangeList.add(new MelangeEntity());
        melangeList.add(new MelangeEntity());
        when(melangeRepository.findAll()).thenReturn(melangeList);

        Set<MelangeEntity> result = melangeService.getAll();

        Assertions.assertEquals(2, result.size());
    }
    
    @Test
    public void testGetOne_OK() {
        MelangeEntity melangeEntity = new MelangeEntity();
        when(melangeRepository.findById(anyLong())).thenReturn(Optional.of(melangeEntity));

        MelangeEntity result = melangeService.getOne(1L);

        Assertions.assertEquals(melangeEntity, result);
    }

    @Test
    public void testGetOne_NotFound() {
 
        when(melangeRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, ()->melangeService.getOne(-99L));
        Assertions.assertEquals("Mélange not found", exception.getMessage());
    }
    
    @Test
    public void testInsert_OK() {
        MelangeForm form = new MelangeForm("Miam","Le meilleur");

        melangeService.insert(form);

        Mockito.verify(melangeRepository, Mockito.times(1)).save(Mockito.any(MelangeEntity.class));
    }

    @Test
    public void testInsert_melangeAlreadyExist() {
        MelangeForm form = new MelangeForm("Miam","Le meilleur");

        when(melangeRepository.exists(any(Specification.class))).thenReturn(true);
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, ()-> melangeService.insert(form));

        assertEquals("Mélange existe déjà", exception.getMessage());
    }
    
    @Test
    public void testInsert_formNull() {
        MelangeForm form = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> melangeService.insert(form));
        
        assertEquals("form ne peut être null", exception.getMessage());
    }

    @Test
    public void testUpdate_OK() {
        MelangeForm form = new MelangeForm("Miam","Le meilleur");
        when(melangeRepository.existsById(anyLong())).thenReturn(true);

        melangeService.update(1L, form);

        Mockito.verify(melangeRepository, Mockito.times(1)).save(Mockito.any(MelangeEntity.class));
    }

    @Test
    public void testUpdate_melangeAlreadyExist() {
        MelangeForm form = new MelangeForm("Miam","Le meilleur");

        when(melangeRepository.exists(any(Specification.class))).thenReturn(true);
        when(melangeRepository.findOne(any(Specification.class))).thenReturn(Optional.of(new MelangeEntity()));
        
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, ()-> melangeService.update(1L, form));

        assertEquals("Mélange déjà existant", exception.getMessage());
    }

    @Test
    public void testUpdate_formNull() {
        MelangeForm form = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> melangeService.update(1L, form));

        assertEquals("form ne peut être null", exception.getMessage());
    }

    @Test
    public void testUpdate_notFound() {
        MelangeForm form = new MelangeForm("Miam","Le meilleur");
        when(melangeRepository.existsById(anyLong())).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, ()-> melangeService.update(-99L, form));

        assertEquals("Mélange not found", exception.getMessage());
    }
    
}
