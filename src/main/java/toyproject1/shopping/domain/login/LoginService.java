package toyproject1.shopping.domain.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject1.shopping.domain.entity.Member;
import toyproject1.shopping.domain.member.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member idCheck(String loginId){
        Member member = memberRepository.findByLoginId(loginId);
        log.info("idCheck member={}", member);
        return member;
    }

    public Member login(String loginId, String password){
        Member findMember = idCheck(loginId);
        if(!findMember.getPassword().equals(password)){
            return null;
        }
        log.info("login member={}", findMember);
        return findMember;
    }
}
