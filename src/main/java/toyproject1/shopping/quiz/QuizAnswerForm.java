package toyproject1.shopping.quiz;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerForm {

    private Long id;
    private String quiz;
    private String answer;

    private MultipartFile multipartFile;
}
