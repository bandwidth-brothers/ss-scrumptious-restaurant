package com.ss.scrumptious_restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menuitem_order")
@Builder
public class MenuitemOrder {

    @JsonIgnore
    @EmbeddedId
    MenuitemOrderKey id;

    @ManyToOne
    @MapsId("menuitemId")
    @JoinColumn(name = "menuitem_id")
    Menuitem menuitem;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("orderId")
//    @JoinColumn(name = "order_id")
//    Order order;

    Long quantity;
}
