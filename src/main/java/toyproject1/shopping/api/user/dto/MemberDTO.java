package toyproject1.shopping.api.user.dto;

import lombok.*;
import toyproject1.shopping.api.shop.domain.Address;
import toyproject1.shopping.api.user.domain.MemberType;

import javax.persistence.Embedded;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long memberId;

    private MemberType memberType;

    private String name;

    private String birthDate;

    private String loginId;

    private String password;

    private String rePassword;

    @Embedded
    private Address address;

    public void changeMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void changePassword(String password){
        this.password = password;
        this.rePassword = password;
    }
}
