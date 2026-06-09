package poly.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.edu.dao.OrderDetailDAO;
import poly.edu.entity.OrderDetail;
import poly.edu.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailDAO.findByOrderId(orderId);
    }
}
