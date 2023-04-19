package be.technobel.ylorth.fermedelacroixblancherest.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException{

    private final String message;
    public AlreadyExistsException(String message){
        super("Already exists");
        this.message = message;
    }
}
