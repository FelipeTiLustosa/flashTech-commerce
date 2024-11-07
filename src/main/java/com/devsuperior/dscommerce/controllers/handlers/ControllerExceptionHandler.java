package com.devsuperior.dscommerce.controllers.handlers;//handlers =   sao os caras q inteceptan alguma coisa e manipulam alguma coisa pra gente

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice // diz ao aplicativo para direcionar todas as exceções de um certo tipo (neste caso, ResourceNotFoundException) para essa classe
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)// Esse é o recado específico para o recepcionista: “Sempre que alguém tentar acessar um recurso que não existe e encontrar uma ResourceNotFoundException, use este método para lidar com a situação.”
    public ResponseEntity<CustomError> resourceNotFound (ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; // Define o status de erro como 404 // HttpStatus é uma enumeração fornecida pelo Spring Framework que representa os códigos de status HTTP.
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI()); // Cria um erro personalizado com informações sobre o que aconteceu
        /*status.value(): Converte o código de status (404) em um valor numérico que será armazenado no objeto CustomError.*/
        /*request.getRequestURI(): Captura o caminho da requisição que causou o erro. Isso ajuda a identificar qual URL o cliente tentou acessar e onde o problema ocorreu.*/
        return ResponseEntity.status(status).body(err); // Envia a resposta com o status 404 e a mensagem de erro
        /*ResponseEntity.status(status): Aqui, estamos configurando o ResponseEntity para retornar o código de status 404 (o valor da variável status). Esse é o código HTTP que indica que o recurso não foi encontrado.*/
        /*.body(err): Aqui estamos definindo o corpo da resposta como o objeto err, que contém as informações detalhadas do erro (timestamp, código de status, mensagem e URI). Isso é o que o cliente verá ao receber a resposta.*/

        /*HttpServletRequest request: Este parâmetro representa a requisição HTTP original que causou o erro.
        Através dele, conseguimos capturar informações sobre a requisição, como o caminho que o cliente usou para tentar acessar o recurso.*/
    }

    @ExceptionHandler(DatabaseException.class)// Esse é o recado específico para o recepcionista: “Sempre que alguém tentar acessar um recurso que não existe e encontrar uma ResourceNotFoundException, use este método para lidar com a situação.”
    public ResponseEntity<CustomError> database (DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}

