package toyproject1.shopping.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter @Setter
public class Address {

    @NotBlank
    private String zipcode;

    @NotBlank
    private String mainAddress;

    @NotBlank
    private String detailAddress;

    @NotBlank
    private String extraAddress;

    private String totalAddress;

    public Address() {
    }
}
