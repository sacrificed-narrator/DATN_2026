package poly.edu.controller;

import java.net.URLEncoder;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import poly.edu.config.VnpayConfig;
import poly.edu.service.OrderService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	VnpayConfig vnpayConfig;

	@Autowired
	OrderService orderService;

	@PostMapping("/vnpay")
	public String createPayment(HttpServletRequest request, @RequestParam("orderId") Long orderId) throws Exception {

		// Lấy order từ DB
		var order = orderService.findById(orderId);

		long amount = order.getOrderDetails().stream().mapToLong(d -> Math.round(d.getPrice() * d.getQuantity())).sum();

		String vnp_TxnRef = String.valueOf(orderId);
		String vnp_OrderInfo = "Thanh toan don hang " + orderId;
		String vnp_OrderType = "other";
		String vnp_IpAddr = request.getRemoteAddr();

		String vnp_CreateDate = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

		Map<String, String> vnp_Params = new TreeMap<>();
		vnp_Params.put("vnp_Version", "2.1.0");
		vnp_Params.put("vnp_Command", "pay");
		vnp_Params.put("vnp_TmnCode", vnpayConfig.vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", vnp_OrderType);
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", vnpayConfig.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();

		for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
			hashData.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "US-ASCII"))
					.append('&');

			query.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "US-ASCII"))
					.append('&');
		}

		hashData.deleteCharAt(hashData.length() - 1);
		query.deleteCharAt(query.length() - 1);

		String vnp_SecureHash = hmacSHA512(vnpayConfig.vnp_HashSecret, hashData.toString());
		query.append("&vnp_SecureHash=").append(vnp_SecureHash);

		return "redirect:" + vnpayConfig.vnp_PayUrl + "?" + query;
	}

	@GetMapping("/vnpay-return")
	public String paymentReturn(HttpServletRequest request) {

		String orderId = request.getParameter("vnp_TxnRef");
		String responseCode = request.getParameter("vnp_ResponseCode");

		if ("00".equals(responseCode)) {
			orderService.updatePaymentStatus(Long.parseLong(orderId), "PAID");
			return "redirect:/order/success";
		} else {
			return "redirect:/cart/view";
		}
	}

	private String hmacSHA512(String key, String data) throws Exception {
		javax.crypto.Mac hmac512 = javax.crypto.Mac.getInstance("HmacSHA512");
		javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(key.getBytes(), "HmacSHA512");
		hmac512.init(secretKey);

		byte[] bytes = hmac512.doFinal(data.getBytes());

		StringBuilder hash = new StringBuilder();
		for (byte b : bytes) {
			hash.append(String.format("%02x", b));
		}
		return hash.toString();
	}
}