package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.services.OrderService;
import com.devsuperior.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
/*Spring automaticamente:
Serializa Respostas: Os métodos dentro de uma classe anotada com @RestController
retornam objetos que são automaticamente serializados em JSON (ou XML, dependendo
da configuração) para a resposta HTTP. Isso significa que não é necessário usar
@ResponseBody em cada método; essa funcionalidade é incluída implicitamente.
Manuseio de Requisições HTTP: Os métodos dentro de um @RestController podem
responder a requisições HTTP (como GET, POST, PUT, DELETE) usando anotações
como @GetMapping, @PostMapping, @PutMapping e @DeleteMapping.*/
@RequestMapping(value = "/orders")
/*permite que você defina a URL que o método do controlador deve escutar.
Por exemplo, se você usar @RequestMapping("/produtos") em uma classe,
todos os métodos dentro dessa classe serão mapeados para URLs que começam com /produtos.*/
public class OrderController {

    @Autowired
    private OrderService service;
    /*
     * Esse método permite buscar um produto pelo id e retornar o resultado no corpo
     * da resposta. Se o produto for encontrado, ele será retornado com o status 200 (OK).
     * @javadoc
     * */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
        /*ResponseEntity é uma classe usada para definir o status HTTP da
        resposta e o conteúdo da resposta. ok(dto) cria uma resposta com o status HTTP
        200 (OK) e o corpo da resposta contendo o ProductDTO.*/
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


}