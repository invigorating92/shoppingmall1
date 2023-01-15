package toyproject1.shopping.domain.login;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginForm {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public LoginForm(String loginId) {
        this.loginId = loginId;
    }
}
