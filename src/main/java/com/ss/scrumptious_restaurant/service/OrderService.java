package com.ss.scrumptious_restaurant.service;

import com.ss.scrumptious_restaurant.dto.OrderStatusUpdateDto;
import com.ss.scrumptious_restaurant.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersByRestaurant(Long restaurantId);

    void updateOrderStatusByIds(OrderStatusUpdateDto dto);

    Order getOrdersById(Long orderId);
}
