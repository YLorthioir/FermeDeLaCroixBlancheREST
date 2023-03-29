package be.technobel.ylorth.fermedelacroixblancherest.model.entity.sante;

import be.technobel.ylorth.fermedelacroixblancherest.model.entity.bovins.Bovin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Injection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="injection_id", nullable = false)
    private long id;
    @Column(nullable = false)
    private LocalDate dateInjection;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bovin_id")
    private Bovin bovin;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "vaccin_id")
    private Vaccin vaccin;

}
