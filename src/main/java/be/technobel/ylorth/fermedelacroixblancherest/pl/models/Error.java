package be.technobel.ylorth.fermedelacroixblancherest.pl.models;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record Error (
        String message,
        HttpStatus status,
        LocalDateTime requestMadeAt,
        String URI
    ){
}
