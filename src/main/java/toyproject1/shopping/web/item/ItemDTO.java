package toyproject1.shopping.web.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import toyproject1.shopping.domain.item.ItemType;
import toyproject1.shopping.domain.item.upload.UploadFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long ItemId;

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


    private MultipartFile attachFile; //파일 하나 첨부

    private List<MultipartFile> imageFiles; //여러 이미지 파일 첨부
}
