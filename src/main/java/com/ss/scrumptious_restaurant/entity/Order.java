package com.ss.scrumptious_restaurant.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="`ORDER`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @ManyToOne
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MenuitemOrder> menuitemOrder = new HashSet<>();

    private String confirmationCode;


    private ZonedDateTime requestedDeliveryTime;

    private Float orderDiscount;

    @CreationTimestamp
    private ZonedDateTime submittedAt;

    @Builder.Default
    @Column(name = "preparation_status")
    @Enumerated(EnumType.STRING)
    private PreparationStatus status = PreparationStatus.PENDING;


}
