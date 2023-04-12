package be.technobel.ylorth.fermedelacroixblancherest.controller;

import be.technobel.ylorth.fermedelacroixblancherest.exception.AlreadyExistsException;
import be.technobel.ylorth.fermedelacroixblancherest.exception.FaucheInsertException;
import be.technobel.ylorth.fermedelacroixblancherest.exception.NotFoundException;
import be.technobel.ylorth.fermedelacroixblancherest.model.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFound(NotFoundException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
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
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

//        return new ResponseEntity<>(errorDTO, headers, HttpStatus.NOT_FOUND);
        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleAlreadyExistsException(AlreadyExistsException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

//        return new ResponseEntity<>(errorDTO, headers, HttpStatus.NOT_FOUND);
        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );

    }

    @ExceptionHandler(FaucheInsertException.class)
    public ResponseEntity<ErrorDTO> handleFaucheInsertException(FaucheInsertException ex, HttpServletRequest req){

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( ex.getMessage() )
                .requestMadeAt( LocalDateTime.now() )
                .URI( req.getRequestURI() )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

//        return new ResponseEntity<>(errorDTO, headers, HttpStatus.NOT_FOUND);
        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .headers( headers )
                .body( errorDTO );

    }

}
