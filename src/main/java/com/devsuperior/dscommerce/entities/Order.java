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
    private User client;
}
