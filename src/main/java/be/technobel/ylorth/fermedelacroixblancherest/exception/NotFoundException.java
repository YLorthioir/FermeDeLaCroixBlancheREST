package be.technobel.ylorth.fermedelacroixblancherest.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final Object innerData;
    public NotFoundException(Object innerData){
        super("Not found");
        this.innerData = innerData;
    }
}
