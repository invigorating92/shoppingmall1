package toyproject1.shopping.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject1.shopping.domain.login.LoginForm;
import toyproject1.shopping.domain.login.LoginService;
import toyproject1.shopping.domain.entity.Member;
import toyproject1.shopping.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        log.info("로그인 페이지 접속");
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String postLoginForm(@Validated @ModelAttribute(name = "loginForm") LoginForm loginForm, BindingResult bindingResult,
                                HttpServletRequest request){

        if (bindingResult.hasErrors()){
            log.info("로그인 검증 오류");
            return "login/loginForm";
        }

        Member idCheckMember = loginService.idCheck(loginForm.getLoginId());
        if (idCheckMember == null){
            log.info("아이디 존재 X");
            bindingResult.reject("idCheck");
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null){
            log.info("로그인페이지 아이디 비밀번호 불일치");
            bindingResult.reject("notEqual");
            return "login/loginForm";
        }
        //세션 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.MY_SESSION, loginMember);
        session.setAttribute(SessionConst.INFO_CHECK, false);

        //로그인 필터로 추가해준 코드
        String redirectURL = request.getParameter("redirectURL");
        log.info("login redirectURL {}", redirectURL);

        if (redirectURL == null){
         return "redirect:/";
        }

        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
