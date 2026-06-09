package poly.edu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VnpayConfig {

    @Value("${vnpay.tmnCode}")
    public String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    public String vnp_HashSecret;

    @Value("${vnpay.payUrl}")
    public String vnp_PayUrl;

    @Value("${vnpay.returnUrl}")
    public String vnp_ReturnUrl;
}