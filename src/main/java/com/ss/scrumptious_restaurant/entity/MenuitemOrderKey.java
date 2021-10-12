package com.ss.scrumptious_restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MenuitemOrderKey implements Serializable {

    @Column(name = "menuitem_id")
    Long menuitemId;

    @Column(name = "order_id")
    Long orderId;
}
