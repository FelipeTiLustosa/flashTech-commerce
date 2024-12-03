package com.devsuperior.dscommerce.controllers.handlers;//handlers =   sao os caras q inteceptan alguma coisa e manipulam alguma coisa pra gente

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.dto.ValidantionError;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database (DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;// 400 Bad Request é usado quando o servidor não consegue processar a solicitação devido a um problema com os dados enviados pelo cliente, como parâmetros inválidos, formato de dados incorreto, etc.
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> MethodArgumentNotValid (MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;//422 O cliente fez uma requisição válida, mas os dados contêm problemas que impedem o processamento
        ValidantionError err = new ValidantionError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());// coloquei esse Dados inválidos para nao aparece o texto padrão q aparece quando a execacao e extourada
        for (FieldError f : e.getBindingResult().getFieldErrors()){// vai pega os erros referentes as validacoes q fizemos na classe ProductDTO ou em outra
            err.addError(f.getField(),f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }
}

