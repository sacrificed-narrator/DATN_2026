package poly.edu.service;

import java.util.List;
import poly.edu.entity.OrderDetail;

public interface OrderDetailService {

    List<OrderDetail> findByOrderId(Long orderId);
}
