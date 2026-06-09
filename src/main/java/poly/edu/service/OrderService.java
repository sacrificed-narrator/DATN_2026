package poly.edu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import poly.edu.entity.Order;
import poly.edu.entity.OrderRequest;

@Service
public interface OrderService {

	Order create(OrderRequest request);

    // Các phương thức hỗ trợ quản lý đơn hàng
    Order findById(Long id);
    List<Order> findByUsername(String username);
    List<Order> findAll();
    void delete(Long id);
    
    Order create(String address, String paymentMethod);

    void updatePaymentStatus(Long orderId, String status);

    
}
