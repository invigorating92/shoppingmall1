package toyproject1.shopping.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.api.user.dto.MemberDTO;
import toyproject1.shopping.api.user.dto.MemberForm;
import toyproject1.shopping.api.user.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDTO entityToDto(Member member){
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(member.getMemberId())
                .memberType(member.getMemberType())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .rePassword(member.getRePassword())
                .address(member.getAddress())
                .build();
        return memberDTO;
    }
    public Member DtoToEntity(MemberDTO memberDTO){
        Member member = Member.builder()
                .memberId(memberDTO.getMemberId())
                .memberType(memberDTO.getMemberType())
                .name(memberDTO.getName())
                .birthDate(memberDTO.getBirthDate())
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .rePassword(memberDTO.getRePassword())
                .address(memberDTO.getAddress())
                .build();
        return member;
    }

    public Member FormToEntity(MemberForm memberForm){
        Member member = Member.builder()
                .memberType(memberForm.getMemberType())
                .birthDate(memberForm.getBirthDate())
                .name(memberForm.getName())
                .loginId(memberForm.getLoginId())
                .password(memberForm.getPassword())
                .rePassword(memberForm.getRePassword())
                .address(null)
                .build();
        return member;
    }

    public Member memberInfoCheck(String loginId, String password){
        Member findMember = memberRepository.findByLoginId(loginId);
        if(!findMember.getPassword().equals(password)){
            log.info("Member Info Check 비밀번호 불일치");
            return null;
        }
        return findMember;
    }

}
