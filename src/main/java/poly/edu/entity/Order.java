package poly.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();

    private String receiverName; 
    private String phoneNumber;
    private String address;
    
    // ✅ thêm phương thức thanh toán
    private String paymentMethod; // COD | MOMO

    // ✅ trạng thái thanh toán
    private String paymentStatus; // PENDING | PAID

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Thêm cascade để tự động lưu chi tiết
    private List<OrderDetail> orderDetails;
}
