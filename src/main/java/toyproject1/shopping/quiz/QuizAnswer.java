package toyproject1.shopping.quiz;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswer {
    @Id
    @GeneratedValue
    private Long id;

    private String quiz;
    private String answer;

    private String imgOriginalName;
    private String imgStoreName;
}
