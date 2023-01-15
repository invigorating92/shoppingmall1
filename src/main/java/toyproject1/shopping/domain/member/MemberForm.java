package toyproject1.shopping.domain.member;

import lombok.*;
import toyproject1.shopping.domain.member.MemberType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {

    private MemberType memberType = MemberType.NORMAL;

    @NotBlank
    private String name;

    @NotBlank
    private String birthDate;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;
}
