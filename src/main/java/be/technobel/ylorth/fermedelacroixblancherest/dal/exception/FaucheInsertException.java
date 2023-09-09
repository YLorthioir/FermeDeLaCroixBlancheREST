package be.technobel.ylorth.fermedelacroixblancherest.dal.exception;

import lombok.Getter;

@Getter
public class FaucheInsertException extends RuntimeException{

    private final Object innerData;
    public FaucheInsertException(Object innerData){
        super("Impossible d'ajouter cette fauche");
        this.innerData = innerData;
    }
}
