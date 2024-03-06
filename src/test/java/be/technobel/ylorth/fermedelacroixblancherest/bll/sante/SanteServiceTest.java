package be.technobel.ylorth.fermedelacroixblancherest.bll.sante;

import be.technobel.ylorth.fermedelacroixblancherest.bll.service.sante.impl.SanteServiceImpl;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.bovins.BovinRepository;
import be.technobel.ylorth.fermedelacroixblancherest.dal.repository.sante.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SanteServiceTest {
    
    @Mock
    ARepository aRepository;
    
    @Mock
    VaccinRepository vaccinRepository;
    
    @Mock
    InjectionRepository injectionRepository;
    
    @Mock
    BovinRepository bovinRepository;
    
    @Mock
    TraitementRepository traitementRepository;
    
    @Mock
    MaladieRepository maladieRepository;
    
    @InjectMocks
    SanteServiceImpl santeService;

    //-------------------------------- Vaccin ------------------------------------------

}
