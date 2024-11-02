package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true) // Esse parâmetro indica que o método não vai realizar alterações nos dados, apenas fazer consultas.
    public ProductDTO findById(Long id){
        //Optional<Product> result = repository.findById(id);
        Product product = repository.findById(id).get();
         return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional // indica que o método ou classe onde ela está aplicada deve ser executado dentro de uma transação padrão de banco de dados, permitindo tanto operações de leitura quanto de escrita
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity = repository.save(entity);
        return new ProductDTO(entity);

        /*Product entity = modelMapper.map(dto, Product.class); //Pegue o dto e crie um objeto do tipo Product usando as informações que estão nele.
        //Product.class é apenas a forma de especificar ao ModelMapper o tipo de destino para o mapeamento.
        entity = repository.save(entity); // Salva no banco
        return modelMapper.map(entity, ProductDTO.class); // Mapeia de volta para ProductDTO*/

    }

}
