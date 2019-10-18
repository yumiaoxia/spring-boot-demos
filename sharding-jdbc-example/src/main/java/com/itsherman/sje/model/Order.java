package com.itsherman.sje.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "t_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID = -4528442403218510988L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;


    private String buyerName;

    private String memo;

    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class)
    private List<OrderItem> orderItems = new ArrayList<>();
}
