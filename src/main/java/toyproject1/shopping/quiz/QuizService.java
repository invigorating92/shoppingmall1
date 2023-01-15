package toyproject1.shopping.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizAnswer formToEntity(QuizAnswerForm quizAnswerForm){
        MultipartFile multipartFile = quizAnswerForm.getMultipartFile();
        String originalFilename = multipartFile.getOriginalFilename();

        String storeFilename = createStoreFilename(originalFilename);

        QuizAnswer quizAnswer = QuizAnswer.builder().quiz(quizAnswerForm.getQuiz())
                .answer(quizAnswerForm.getAnswer())
                .imgOriginalName(originalFilename)
                .imgStoreName(storeFilename)
                .build();

        return quizAnswer;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid+"."+ext;
        return storeFilename;
    }

    //파일 확장자 추출
    public String extractExt(String fileName){
        int index = fileName.lastIndexOf(".");
        String ext = fileName.substring(index + 1);
        return ext;
    }
}
