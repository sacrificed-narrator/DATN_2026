package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.entity.Order;
import poly.edu.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping("/checkout")
	public String checkout() {
		return "order/checkout";
	}

	@PostMapping("/checkout")
	public String doCheckout(@RequestParam("address") String address,
			@RequestParam("paymentMethod") String paymentMethod, Model model) {

		Order order = orderService.create(address, paymentMethod);

		if (paymentMethod.equals("VNPAY")) {
			model.addAttribute("orderId", order.getId());
			return "order/vnpay";
		}

		return "redirect:/order/success";
	}

	@GetMapping("/success")
	public String success() {
		return "order/success";
	}
}