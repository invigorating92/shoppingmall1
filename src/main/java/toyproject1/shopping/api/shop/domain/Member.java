package toyproject1.shopping.api.shop.domain;

import lombok.*;
import toyproject1.shopping.api.user.domain.MemberType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{
    //field
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;

    @NotBlank
    private String name;

    @NotBlank
    private String birthDate;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @Transient
    private String rePassword;

    @Embedded
    private Address address;

    public void changeAddress(Address address){
        this.address=address;
    }

    //constructor
    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }
}
