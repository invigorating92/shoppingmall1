package toyproject1.shopping.api.shop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item extends BaseEntity{
    //field
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

//    private Long totalId;

    @NotBlank(message = "상품 이름을 입력해주세요")
    private String itemName;

    @NotNull(message = "상품 종류를 선택해주세요")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @NotNull(message = "상품 가격을 입력해주세요")
    @Min(1000)
    private Integer price;

    @NotNull(message = "상품 수량을 입력해주세요")
    @Max(9999)
    private Integer quantity;

    @Lob
    @NotBlank(message = "상품 상세 설명을 입력해주세요")
    private String itemDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    private UploadFile attachFile; //파일 하나 첨부
//
//    private List<UploadFile> imageFiles; //여러 이미지 파일 첨부

//    public void insertUploadFile(UploadFile attachFile, List<UploadFile> imageFiles){
//        this.attachFile = attachFile;
//        this.imageFiles=imageFiles;
//    }
    //constructor

}
