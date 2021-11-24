package com.ss.scrumptious_restaurant.controller;

import com.ss.scrumptious.common_entities.entity.Order;
import com.ss.scrumptious_restaurant.dto.OrderStatusUpdateDto;
import com.ss.scrumptious_restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @GetMapping("/restaurants/{restaurantId}/orders")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@PathVariable Long restaurantId) {
        List<Order> orders = orderService.getOrdersByRestaurant(restaurantId);
        if (orders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @PutMapping("/restaurants/{restaurantId}/orders")
    public ResponseEntity<Void> updateOrderStatusByIds(@PathVariable Long restaurantId,
                                                       @RequestBody OrderStatusUpdateDto dto) {
        log.info("OrderStatusUpdateDto: " + dto);
        orderService.updateOrderStatusByIds(dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrdersById(@PathVariable Long orderId) {
        Order order = orderService.getOrdersById(orderId);
        return ResponseEntity.ok(order);
    }
}
