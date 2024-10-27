package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    /*Em resumo, quando você usa TIMESTAMP WITHOUT TIME ZONE,
     a data e hora são armazenadas exatamente como estão na aplicação,
      sem nenhuma alteração ou ajuste relacionado a fuso horário.
    Se a data e hora na sua máquina, ou na aplicação, forem 2024-10-23 15:30:00,
     esse valor será salvo dessa maneira no banco de dados, sem qualquer modificação.
      O banco não fará nenhuma conversão de fuso horário e não adicionará
       nenhuma informação extra sobre o fuso.
    Portanto, a data e hora armazenadas são uma cópia exata do que foi
     fornecido pela aplicação, sem qualquer consideração sobre o fuso horário.*/
    private Instant moment;
    private OrderStatus status;

    @ManyToOne //Relacionamento de muitos para um
    @JoinColumn(name = "client_id")
    /*Define o nome da coluna de chave estrangeira na tabela
    da entidade atual (Order) como client_id.
    Mapeia o relacionamento entre Order e User, permitindo que
     o Order se relacione com um User específico.*/
    private User client;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public Order() {
    }

    public Order(Long id, Instant moment, OrderStatus status, User client, Payment payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
