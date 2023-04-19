package be.technobel.ylorth.fermedelacroixblancherest.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final String message;
    public NotFoundException(String message){
        super("Not found");
        this.message = message;
    }
}
