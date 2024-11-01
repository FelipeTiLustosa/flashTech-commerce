package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
/*Spring automaticamente:
Serializa Respostas: Os métodos dentro de uma classe anotada com @RestController
retornam objetos que são automaticamente serializados em JSON (ou XML, dependendo
da configuração) para a resposta HTTP. Isso significa que não é necessário usar
@ResponseBody em cada método; essa funcionalidade é incluída implicitamente.
Manuseio de Requisições HTTP: Os métodos dentro de um @RestController podem
responder a requisições HTTP (como GET, POST, PUT, DELETE) usando anotações
como @GetMapping, @PostMapping, @PutMapping e @DeleteMapping.*/
@RequestMapping(value = "/products")
/*permite que você defina a URL que o método do controlador deve escutar.
Por exemplo, se você usar @RequestMapping("/produtos") em uma classe,
todos os métodos dentro dessa classe serão mapeados para URLs que começam com /produtos.*/
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

}
