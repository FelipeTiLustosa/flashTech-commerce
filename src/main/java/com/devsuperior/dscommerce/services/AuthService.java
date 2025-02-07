package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public  void  validateSelfOrAdmin(Long userId){
        //Vou pega o usuario autenticado
        User me = userService.authenticated();
        //logica do if: este usuario me nao e admin e tambem o usuario me nao e o mesmo usuario desse id que eu envie como parametro
        //caso for verdade ele nao e nem admin e nem o proprio usuario, do argumento que chegou aqui no metado entao eu lanço uma exceção
        if (!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)){
            throw new ForbiddenException("Access denied"); //acesso negado
        }
    }
}
