package com.devsuperior.dscommerce.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

    @Embeddable
    /* é usada para criar uma classe cujas instâncias
    não representam uma entidade independente, mas, sim,
    são integradas como parte de outra entidade.
Como funciona:
Quando uma classe é marcada com @Embeddable, significa
 que os atributos dessa classe serão armazenados na mesma
 tabela da entidade que a contém.
A classe com @Embeddable não possui uma tabela própria no banco
 de dados. Em vez disso, ela fornece atributos que se integram
 à tabela da entidade que a incorpora*/
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItemPK() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
