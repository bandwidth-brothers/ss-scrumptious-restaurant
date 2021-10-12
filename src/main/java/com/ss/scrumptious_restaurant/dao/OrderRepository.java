package com.ss.scrumptious_restaurant.dao;

import com.ss.scrumptious_restaurant.entity.Order;
import com.ss.scrumptious_restaurant.entity.PreparationStatus;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByRestaurant(Restaurant r);

    @Modifying
    @Transactional
    @Query("update Order o set o.status = ?1 where o.id in ?2")
    void updateStatusByIdIn(PreparationStatus status, List<Long> ids);


}
