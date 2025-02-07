package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService   {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true) // Esse parâmetro indica que o método não vai realizar alterações nos dados, apenas fazer consultas.
    public OrderDTO findById(Long id){
        Order order  = repository.findById(id).orElseThrow(// vai tenta acessa o obj, caso nao encontre vai lanca uma exceção
                ()-> new ResourceNotFoundException("Recurso não encontrado"));// exceção criada
        return new OrderDTO(order);
    }
}
