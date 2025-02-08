package com.devsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidantionError extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidantionError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    // ser eu chamar esse metado mais de uma vez com o mesmo nome do campo ele vai adiciona mais de um erro para essse campo
    // no caso eu quero q cada campo da entidade tenha apenas um erro, no front eu vou trata um erro de cada vez
    public  void addError(String fieldName,String message){
        // Eu vou remover um possivel msg com esse mesmo nome que ja exista e vou adiciona uma nova msg
        errors.removeIf(x -> x.getFieldName().equals(fieldName));
        errors.add(new FieldMessage(fieldName,message));
    }
}
