package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true) // Esse parâmetro indica que o método não vai realizar alterações nos dados, apenas fazer consultas.
    public ProductDTO findById(Long id){
        //Optional<Product> result = repository.findById(id);
        Product product = repository.findById(id).orElseThrow(// vai tenta acessa o obj, caso nao encontre vai lanca uma exceção
                ()-> new ResourceNotFoundException("Recurso não encontrado"));// exceção criada
         return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name,Pageable pageable){
        Page<Product> result = repository.searchByName(name,pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional // indica que o método ou classe onde ela está aplicada deve ser executado dentro de uma transação padrão de banco de dados, permitindo tanto operações de leitura quanto de escrita
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);

//        Product entity = modelMapper.map(dto, Product.class); //Pegue o dto e crie um objeto do tipo Product usando as informações que estão nele.
//        //Product.class é apenas a forma de especificar ao ModelMapper o tipo de destino para o mapeamento.
//        System.out.println("TESTE");
//        entity = repository.save(entity); // Salva no banco
//        return modelMapper.map(entity, ProductDTO.class); // Mapeia de volta para ProductDTO
    }

    @Transactional // indica que o método ou classe onde ela está aplicada deve ser executado dentro de uma transação padrão de banco de dados, permitindo tanto operações de leitura quanto de escrita
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);//Vai intancia apenas com a referencia do product q for passado como argumeto
            /*getReferenceById e diferente pq ela nao vai no banco de dados, vai ser um obj monitorado pela jpa, */
            copyDtoToEntity(dto,entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    /*@Transactional: Indica que o método deve ser executado dentro de uma transação.
    propagation = Propagation.SUPPORTS: Diz que o método deve aproveitar uma transação ativa se houver uma, mas não criará uma nova transação caso não exista nenhuma.*/
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }


}
