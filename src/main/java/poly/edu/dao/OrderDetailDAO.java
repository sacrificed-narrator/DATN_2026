package poly.edu.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long orderId);
}
