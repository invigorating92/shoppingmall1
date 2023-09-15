package toyproject1.shopping.api.user.dto;

import lombok.*;
import toyproject1.shopping.api.user.domain.MemberType;

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
