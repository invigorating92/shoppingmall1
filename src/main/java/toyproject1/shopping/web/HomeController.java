package toyproject1.shopping.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session == null){
           log.info("세션 없음");
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.MY_SESSION);
        if (loginMember == null){
            log.info("로그인 정보X");
            return "home";
        }

        model.addAttribute("member", loginMember);

        log.info("MemberType={}", loginMember.getMemberType());
        session.setAttribute(SessionConst.INFO_CHECK, false);
        return "loginHome";
    }
}
