package com.itsherman.sje.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_order_item")
public class OrderItem extends BaseEntity {

    private static final long serialVersionUID = 1699027021042037338L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderItemId;

    private String itemName;

    private long quantity;

    private LocalDateTime payTime;


    @ManyToOne
    private Order order;
}
