package be.technobel.ylorth.fermedelacroixblancherest.dal.models.sante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Vaccin")
public class VaccinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="vaccin_id", nullable = false)
    @Getter
    private long id;
    @Getter
    @Column(nullable = false, unique = true)
    private String nom;
    @Getter
    private int nbDose;
    @Getter
    private int delai;
    @Getter
    private String dosage;
    @Getter @Setter
    @Column(nullable = false)
    private boolean actif;

    // Getters & Setters customs

    // Setters
    public void setId(long id){
        if(id <= 0)
            throw new IllegalArgumentException("Id incorrecte");

        this.id = id;
    }
    public void setNom(String nom) {
        if(nom==null || nom.equals(""))
            throw new IllegalArgumentException("Nom invalide");

        this.nom = nom;
    }

    public void setNbDose(int nbDose) {
        if(nbDose <=0)
            throw new IllegalArgumentException("Nombre dose invalide");

        this.nbDose = nbDose;
    }

    public void setDelai(int delai) {
        if(delai <0)
            throw new IllegalArgumentException("Délai invalide");

        this.delai = delai;
    }

    public void setDosage(String dosage) {
        if(dosage==null || dosage.equals(""))
            throw new IllegalArgumentException("Dosage invalide");

        this.dosage = dosage;
    }
}
