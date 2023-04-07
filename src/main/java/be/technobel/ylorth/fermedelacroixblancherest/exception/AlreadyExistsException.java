package be.technobel.ylorth.fermedelacroixblancherest.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException{

    private final Object innerData;
    public AlreadyExistsException(Object innerData){
        super("Already exists");
        this.innerData = innerData;
    }
}
