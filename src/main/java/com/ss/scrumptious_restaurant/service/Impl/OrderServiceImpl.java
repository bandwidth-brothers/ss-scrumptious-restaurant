package com.ss.scrumptious_restaurant.service.Impl;

import com.ss.scrumptious_restaurant.dao.OrderRepository;
import com.ss.scrumptious_restaurant.dto.OrderStatusUpdateDto;
import com.ss.scrumptious_restaurant.entity.Order;
import com.ss.scrumptious_restaurant.entity.PreparationStatus;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.service.OrderService;
import com.ss.scrumptious_restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;

    @Override
    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        Restaurant res = restaurantService.getRestaurantById(restaurantId);
        List<Order> list = orderRepository.findAllByRestaurant(res);
        return list;
    }

    @Override
    public void updateOrderStatusByIds(OrderStatusUpdateDto dto) {
        orderRepository.updateStatusByIdIn(PreparationStatus.valueOf(dto.getStatus()), dto.getIds());

    }

    @Override
    public Order getOrdersById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("item no exist: " + orderId));
        return order;
    }
}
