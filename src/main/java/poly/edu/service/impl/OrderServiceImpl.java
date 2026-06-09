package poly.edu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.dao.OrderDAO;
import poly.edu.dao.OrderDetailDAO;
import poly.edu.dao.ProductDAO;
import poly.edu.entity.Order;
import poly.edu.entity.OrderDetail;
import poly.edu.entity.OrderRequest;
import poly.edu.entity.Product;
import poly.edu.model.CartItem;
import poly.edu.service.AuthService;
import poly.edu.service.CartService;
import poly.edu.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
    OrderDAO orderDAO; //

    @Autowired
    OrderDetailDAO orderDetailDAO; //

    @Autowired
    ProductDAO productDAO; //

    @Autowired
    CartService cartService; //

    @Autowired
    AuthService authService; //

    @Override
    @Transactional // Đảm bảo an toàn dữ liệu khi lưu nhiều bảng
    public Order create(OrderRequest request) {
    	Order order = new Order();
        order.setAccount(authService.getUser());
        order.setReceiverName(request.getReceiverName());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setAddress(request.getAddress());
        order.setCreateDate(new Date());

        // 1. Lưu đơn hàng chính
        Order savedOrder = orderDAO.save(order);
        
        // 2. Tạo danh sách để giữ lại chi tiết sản phẩm
        List<OrderDetail> details = new ArrayList<>();

        for (CartItem item : cartService.getItems()) {
            Product product = productDAO.findById(item.getId()).orElse(null);
            if (product != null) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(savedOrder);
                detail.setProduct(product);
                detail.setPrice(item.getPrice());
                detail.setQuantity(item.getQuantity());
                
                orderDetailDAO.save(detail);
                details.add(detail); // Thêm vào danh sách tạm
            }
        }

        // 3. Quan trọng: Gán danh sách chi tiết vào order để hiển thị ở view
        savedOrder.setOrderDetails(details);

        // 4. Bây giờ mới xóa giỏ hàng
        cartService.clear();
        
        return savedOrder;
    }
    
    public Order create(String address, String paymentMethod) {

		Order order = new Order();
		order.setAccount(authService.getUser());
		order.setAddress(address);
		order.setPaymentMethod(paymentMethod);

		if(paymentMethod.equals("MOMO")) {
			order.setPaymentStatus("PENDING");
		} else {
			order.setPaymentStatus("PAID"); // COD coi như thanh toán sau
		}

		orderDAO.save(order);

		for (CartItem item : cartService.getItems()) {

			Product product = productDAO.findById(item.getId()).orElse(null);

			if (product != null) {
				OrderDetail detail = new OrderDetail();
				detail.setOrder(order);
				detail.setProduct(product);
				detail.setPrice(item.getPrice());
				detail.setQuantity(item.getQuantity());

				orderDetailDAO.save(detail);
			}
		}

		// ✅ chỉ clear cart nếu COD
		if(paymentMethod.equals("COD")) {
			cartService.clear();
		}

		return order;
	}

	@Override
	public void updatePaymentStatus(Long orderId, String status) {
		Order order = orderDAO.findById(orderId).orElse(null);
		if(order != null) {
			order.setPaymentStatus(status);
			orderDAO.save(order);
			cartService.clear(); // clear khi thanh toán thành công
		}
	}

    @Override
    public List<Order> findByUsername(String username) {
        return orderDAO.findByAccountUsername(username); //
    }

    @Override
    public Order findById(Long id) {
        return orderDAO.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        orderDAO.deleteById(id);
    }
    
    
}
