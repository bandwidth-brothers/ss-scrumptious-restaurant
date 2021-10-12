package com.ss.scrumptious_restaurant.controller;

import com.ss.scrumptious_restaurant.dao.OrderRepository;
import com.ss.scrumptious_restaurant.dto.OrderStatusUpdateDto;
import com.ss.scrumptious_restaurant.entity.Order;
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
    private final OrderRepository orderRepository;

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @GetMapping("/restaurants/{rid}/orders")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@PathVariable Long rid) {
        List<Order> orders = orderService.getOrdersByRestaurant(rid);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @PutMapping("/restaurants/{rid}/orders")
    public ResponseEntity<Void> updateOrderStatusByIds(@PathVariable Long rid,
                                                       @RequestBody OrderStatusUpdateDto dto) {
        log.info("OrderStatusUpdateDto: " + dto);
        orderService.updateOrderStatusByIds(dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @GetMapping("/{oid}")
    public ResponseEntity<Order> getOrdersById(@PathVariable Long oid) {
        Order order = orderService.getOrdersById(oid);
        return ResponseEntity.ok(order);
    }
}
