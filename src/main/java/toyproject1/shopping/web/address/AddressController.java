package toyproject1.shopping.web.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject1.shopping.api.shop.domain.Address;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.api.user.repository.MemberRepository;
import toyproject1.shopping.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AddressController {
    private final MemberRepository memberRepository;
    private final AddressService addressService;

    @GetMapping("/address/add")
    public String GetAddAddress(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        boolean infoCheck = (boolean) session.getAttribute(SessionConst.INFO_CHECK);

        if (!infoCheck){
            log.info("INFO_CHECK = false");
            return "redirect:/members/info";
        }
        model.addAttribute("address", new Address());
        return "address/addForm";
    }

    @Transactional
    @PostMapping("/address/add")
    public String PostAddAddress(@Validated @ModelAttribute(name = "address") Address address, BindingResult bindingResult,
                                 HttpServletRequest request){
        HttpSession session = request.getSession();
        Member sessionMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        if (bindingResult.hasErrors()){
            log.info("주소 입력 오류");
            return "address/addForm";
        }

        boolean infoCheck = (boolean) session.getAttribute(SessionConst.INFO_CHECK);
        if (!infoCheck){
            log.info("INFO_CHECK = false");
            return "redirect:/members/info";
        }

        try{
            Address newAddress = addressService.createAddress(address);
            log.info("newAddress={}", newAddress.getTotalAddress());

            sessionMember.changeAddress(newAddress); //setter대신 내부 메서드를 이용하여 이름을 지음으로써 가독성 높임
            log.info("sessionMember changeAddress={}", sessionMember.getAddress().getTotalAddress());
            memberRepository.save(sessionMember);
        } catch (Exception e){
            log.error("error={}",e);
        }
        return "redirect:/members/info";
    }
}
