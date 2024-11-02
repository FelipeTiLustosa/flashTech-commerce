package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ProductDTO insert(@RequestBody ProductDTO dto) {
        return service.insert(dto);
        /*@RequestBody, você está basicamente dizendo ao Spring que:
        "Eu quero que você pegue os dados do corpo da requisição HTTP." Isso é
        especialmente útil em requisições do tipo POST ou PUT, onde você está
        enviando dados para serem processados ou armazenados.
        "Transforme esses dados em um objeto Java." O Spring automaticamente pega os
        dados que você enviou (geralmente em formato JSON ou XML) e os converte em um
        objeto da classe que você especificou
        como parâmetro do método. No seu caso, é o ProductDTO.
        "Por favor, use esse objeto para executar o que eu preciso." Assim, você pode utilizar
        esse objeto já preenchido no seu método, facilitando a manipulação e a lógica de negócios, sem precisar extrair manualmente cada campo do corpo da requisição.
        Resumindo:
        A anotação @RequestBody simplifica a recepção de dados em um controlador Spring,
        permitindo que você trabalhe diretamente com objetos Java, que representam os dados
        que foram enviados pelo cliente. Portanto, você está declarando que deseja que o
        Spring faça esse trabalho de forma automática, economizando tempo e reduzindo a
        possibilidade de erros ao manipular os dados manualmente*/
    }

}
