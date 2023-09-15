package toyproject1.shopping.web.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject1.shopping.api.shop.domain.OrderItem;
import toyproject1.shopping.api.shop.repository.OrderRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/order")
    public String OrderPageCreate(RedirectAttributes redirectAttributes){
        OrderItem orderItem = new OrderItem();
        redirectAttributes.addAttribute("orderId", orderItem.getId());
        return "redirect:/order/{orderId}";
    }

    @GetMapping("/order/{orderId}")
    public String OrderPage(@PathVariable Long orderId, Model model){
        OrderItem orderItem = orderRepository.findById(orderId);
        model.addAttribute("orderItem", orderItem);
        return "order/orderPage";
    }
}
