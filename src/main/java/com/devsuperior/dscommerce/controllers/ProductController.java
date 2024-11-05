package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/products")
/*permite que você defina a URL que o método do controlador deve escutar.
Por exemplo, se você usar @RequestMapping("/produtos") em uma classe,
todos os métodos dentro dessa classe serão mapeados para URLs que começam com /produtos.*/
public class ProductController {

    @Autowired
    private ProductService service;
    /*
    * Esse método permite buscar um produto pelo id e retornar o resultado no corpo
    * da resposta. Se o produto for encontrado, ele será retornado com o status 200 (OK).
    * @javadoc
    * */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
        /*ResponseEntity é uma classe usada para definir o status HTTP da
        resposta e o conteúdo da resposta. ok(dto) cria uma resposta com o status HTTP
        200 (OK) e o corpo da resposta contendo o ProductDTO.*/
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);

        /*Então, ao retornar ResponseEntity<Page<ProductDTO>>, você está enviando
        de volta uma página de produtos (com as informações de paginação) dentro
        de uma resposta HTTP completa.*/
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

        /*URI é um identificador para um recurso específico, como uma URL*/

        /*ServletUriComponentsBuilder.fromCurrentRequest(): Começa a construir uma URI a partir da URL atual (por exemplo, /products).
        .path("/{id}"): Adiciona /{id} ao final da URL atual, que será o caminho do novo recurso.
        .buildAndExpand(dto.getId()): Substitui {id} com o ID do produto recém-criado
        (dto.getId()). Isso cria uma URI completa para acessar o novo produto, como /products/123, onde 123 é o ID gerado.
        .toUri(): Converte o resultado para um objeto URI.*/

        /*Esse método insere um novo produto no banco de dados e retorna uma resposta com o status 201 (Created), o objeto ProductDTO no corpo da resposta e um cabeçalho Location contendo a URL do novo recurso.*/

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

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

}
