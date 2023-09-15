package toyproject1.shopping.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.api.user.dto.MemberForm;
import toyproject1.shopping.api.user.repository.MemberRepository;
import toyproject1.shopping.api.user.service.MemberService;
import toyproject1.shopping.api.user.domain.MemberType;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class MemberRegister {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    void memberRegister(){
        MemberForm member = new MemberForm();
        member.setName("김현우1");
//        member.setMemberId(1L);
        member.setBirthDate("920228");
        member.setLoginId("invigorating");
        member.setMemberType(MemberType.NORMAL);
        member.setPassword("123");
        member.setRePassword("123");
        Member memberEntity = memberService.FormToEntity(member);
        memberRepository.save(memberEntity);

    }

    @Test
    void findMembers(){
        List<Member> all2 = memberRepository.findAll2();
        for (Member member : all2) {
            System.out.println("member = " + member);
        }

        Member member = memberRepository.findById(7L);
        System.out.println("member = " + member.toString());

    }
}
