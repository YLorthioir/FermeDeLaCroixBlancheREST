package be.technobel.ylorth.fermedelacroixblancherest.pl.controller;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.exception.FaucheInsertException;
import be.technobel.ylorth.fermedelacroixblancherest.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.pl.models.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFound(NotFoundException ex, HttpServletRequest req){

        Error errorDTO = Error.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

//        return new ResponseEntity<>(errorDTO, headers, HttpStatus.NOT_FOUND);
        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest req){

        Error errorDTO = Error.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Error> handleAlreadyExistsException(AlreadyExistsException ex, HttpServletRequest req){

        Error errorDTO = Error.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(FaucheInsertException.class)
    public ResponseEntity<Error> handleFaucheInsertException(FaucheInsertException ex, HttpServletRequest req){

        Error errorDTO = Error.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleInvalidForm(MethodArgumentNotValidException ex, HttpServletRequest req){

        Map<String,String> errorMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(e -> {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            errorMap.put(field,message);
        });

        String errorsList = "";

        for (Map.Entry<String, String> stringStringEntry : errorMap.entrySet()) {
            errorsList += stringStringEntry.getKey() + " - " + stringStringEntry.getValue() + " \n ";
        }

        Error errorDTO = Error.builder()
                .status( HttpStatus.BAD_REQUEST)
                .message( errorsList )
                .requestMadeAt(LocalDateTime.now())
                .URI(req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );
    }

}
