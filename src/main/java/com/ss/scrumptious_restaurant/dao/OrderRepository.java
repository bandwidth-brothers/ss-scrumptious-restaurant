package com.ss.scrumptious_restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import com.ss.scrumptious.common_entities.entity.Order;
import com.ss.scrumptious.common_entities.entity.PreparationStatus;
import com.ss.scrumptious.common_entities.entity.Restaurant;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByRestaurant(Restaurant r);

    // TODO: fix this
    // @Modifying
    // @Transactional
    // @Query("update Order o set o.status = ?1 where o.id = ?2")
    // void updateStatusByIdIn(PreparationStatus status, List<Long> ids);


}
