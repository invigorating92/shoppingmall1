package toyproject1.shopping.api.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PwModForm {
    @NotBlank
    private String passwordNow;
    @NotBlank
    private String passwordNew;
    @NotBlank
    private String passwordNewCheck;
}
