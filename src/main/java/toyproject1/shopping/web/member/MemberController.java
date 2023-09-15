package toyproject1.shopping.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject1.shopping.api.user.domain.MemberType;
import toyproject1.shopping.api.user.dto.PwModForm;
import toyproject1.shopping.api.auth.LoginForm;
import toyproject1.shopping.api.user.dto.MemberDTO;
import toyproject1.shopping.api.user.dto.MemberForm;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.api.user.repository.MemberRepository;
import toyproject1.shopping.api.user.service.MemberService;
import toyproject1.shopping.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/agree")
    public String agree(Model model){
        Agreement agreement = new Agreement();
        model.addAttribute("agreement", agreement);
        return "members/agree";
    }

    @PostMapping("/agree")
    public String postAgree(@ModelAttribute(name = "agreement") Agreement agreement, BindingResult bindingResult){
        Boolean agree = agreement.getAgree();
        log.info("agree={}", agree);
        if(agree == false || agree == null){
            bindingResult.rejectValue("agree", "notAgree", "동의를 하셔야 합니다.");
            log.info("동의 체크 안함");
            return "members/agree";
        }

        log.info("동의 체크 함");
        return "redirect:/members/add";
    }

    @GetMapping("/add")
    public String getAdd(Model model){
        model.addAttribute("member", new MemberForm());
        return "members/addForm";
    }

    @Transactional
    @PostMapping("/add")
    public String postAdd(@Validated @ModelAttribute(name = "member") MemberForm memberForm, BindingResult bindingResult){
        String password = memberForm.getPassword();
        String rePassword = memberForm.getRePassword();

        if (bindingResult.hasErrors()){
            log.info("검증 오류");
            return "members/addForm";
        }

        String loginId = memberForm.getLoginId();
        Member existLoginId = memberRepository.findByLoginId(loginId);
        if(existLoginId != null){
            bindingResult.reject("existLoginId");
            return "members/addForm";
        }

        if(!password.equals(rePassword)){
            log.info("비밀번호 일치안함");
            log.info("password={}, rePassword={}",memberForm.getPassword(), memberForm.getRePassword());
            bindingResult.reject("notEqual");
            return "members/addForm";
        }
        log.info("비밀번호 일치");
        Member member = memberService.FormToEntity(memberForm);
        memberRepository.save(member);
        return "redirect:/";
    }

    @GetMapping("/seller/register")
    public String registerSeller(Model model){
        Agreement agreement = new Agreement();
        model.addAttribute("agreement", agreement);
        return "members/seller/sellerForm";
    }

    @Transactional
    @PostMapping("/seller/register")
    public String PostRegisterSeller(@ModelAttribute("agreement") Agreement agreement ,HttpServletRequest request, BindingResult bindingResult){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        Boolean agree = agreement.getAgree();
        log.info("agree={}", agree);
        if (agree == null || agree == false){
            bindingResult.rejectValue("agree", "notAgreeSeller", "판매자 등록X");
         return "members/seller/sellerForm";
        }
        MemberDTO memberDTO = memberService.entityToDto(loginMember);
        log.info("MemberDTO MemberType={}", memberDTO.getMemberType());
        memberDTO.changeMemberType(MemberType.SELLER);
        log.info("SELLER Change MemberDTO MemberType={}", memberDTO.getMemberType());
        Member member = memberService.DtoToEntity(memberDTO);
        memberRepository.save(member);

        log.info("find MemberType={}", member.getMemberType());
        return "redirect:/logout";
    }

    @GetMapping("/info")
    public String memberInfo(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.MY_SESSION);
//        if (sessionMember.getMemberId() != memberId){
//            log.info("세션memberId, 접속memberId 비일치 -> redirect");
//            return "redirect:/members/info/" + sessionMember.getMemberId();
//        }

//        Member findMember = memberRepository.findById(memberId);
        MemberDTO memberDTO = memberService.entityToDto(sessionMember);
        model.addAttribute("member", memberDTO);
        return "members/memberInfo";
    }

    @GetMapping("/info/modify/password")
    public String memberInfoModifyPassword(Model model){
        log.info("패스워드 변경 페이지 접속");
        model.addAttribute("pwForm", new PwModForm());
        return "members/memberInfoPassword";
    }

    @Transactional
    @PostMapping("/info/modify/password")
    public String memberInfoModifyPasswordPost(@Validated @ModelAttribute(name = "pwForm") PwModForm pwForm, BindingResult bindingResult, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        if (bindingResult.hasErrors()){
            log.info("validation 입력 오류");
            return "members/memberInfoPassword";
        }

        if (!sessionMember.getPassword().equals(pwForm.getPasswordNow())){
            log.info("현재 비밀번호 불일치");
            log.info("pwFrom.getPasswordNow={}", pwForm.getPasswordNow());
            bindingResult.reject("notEqual");
            return "members/memberInfoPassword";
        }
        if (!pwForm.getPasswordNew().equals(pwForm.getPasswordNewCheck())){
            log.info("새로운 비밀번호 불일치");
            bindingResult.reject("notEqual","새로운 비밀번호 불일치");
            return "members/memberInfoPassword";
        }
        if (pwForm.getPasswordNow().equals(pwForm.getPasswordNew())){
            log.info("현재 비밀번호 = 새로운 비밀번호 일치 -> 다른 비밀번호");
            bindingResult.reject("equal");
            return "members/memberInfoPassword";
        }
        MemberDTO memberDTO = memberService.entityToDto(sessionMember);
        memberDTO.changePassword(pwForm.getPasswordNewCheck());
        Member member = memberService.DtoToEntity(memberDTO);
        memberRepository.save(member);
        log.info("비밀번호 변경 완료");
        return "redirect:/logout";
    }

    @GetMapping("/check")
    public String checkMember(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        model.addAttribute("loginForm", new LoginForm(sessionMember.getLoginId()));

        return "members/memberInfoCheck";
    }

    @PostMapping("/check")
    public String checkMemberPost(@Validated @ModelAttribute(name = "loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

//        if (bindingResult.hasErrors()){
//            log.info("검증 오류");
//            return "members/memberInfoCheck";
//        }
        try{
            Member member = memberService.memberInfoCheck(loginForm.getLoginId(), loginForm.getPassword());
            if (member == null){
                log.info("비밀번호 체크 불일치");
                return "members/memberInfoCheck";
            }
//            redirectAttributes.addAttribute("memberId", member.getMemberId());
        } catch (Exception e){
            log.error("info check error={}",e);
        }

        session.setAttribute(SessionConst.INFO_CHECK, true);
        return "redirect:/members/info";
    }


}
