package toyproject1.shopping.quiz;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private List<QuizAnswer> randomQuiz = new ArrayList<>();
    private List<QuizAnswer> randomAnswer = new ArrayList<>();
    private int correct=0;
    private int wrong=0;
    private int QUIZ_TOTAL_NUM = 6;
    private int ANSWER_TOTAL_NUM = 5;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/start")
    public String quizStartPage(){
        correct=0;
        wrong=0;
        randomQuiz.clear();
        List<QuizAnswer> all = quizRepository.findAll();
        Collections.shuffle(all);
        for(int i=0; i<=QUIZ_TOTAL_NUM;i++){
            randomQuiz.add(all.get(i));
        }
        return "quiz/start";
    }

    @GetMapping("/quiz/create")
    public String createQuiz(Model model){
        QuizAnswerForm quizAnswerForm = new QuizAnswerForm();
        model.addAttribute("quizAnswer", quizAnswerForm);
        return "quiz/create";
    }

    @PostMapping("/quiz/create")
    public String createQuizPost(@ModelAttribute QuizAnswerForm quizAnswerForm, BindingResult bindingResult) throws IOException {
        try{
            MultipartFile multipartFile = quizAnswerForm.getMultipartFile();
            if (!multipartFile.isEmpty()){

            QuizAnswer quizAnswer = quizService.formToEntity(quizAnswerForm);
            quizRepository.save(quizAnswer);

            multipartFile.transferTo(new File(fileDir+quizAnswer.getImgStoreName()));
            }

        }catch (Exception e){
         log.error("error message={}", e.getMessage());
        }
        return "redirect:/quiz/create";
    }

    @GetMapping("/quiz/{quizOrder}")
    public String quizPage(@PathVariable int quizOrder, Model model){

        QuizAnswer quizAnswer = randomQuiz.get(quizOrder);
        model.addAttribute("quizAnswer", quizAnswer);

        List<String> answerList = new ArrayList<>();
        answerList.add(quizAnswer.getAnswer());

        List<QuizAnswer> all = quizRepository.findAll();
        Collections.shuffle(all);
        for(int i=0; i<all.size();i++){
            if (!all.get(i).getAnswer().equals(quizAnswer.getAnswer())){
                answerList.add(all.get(i).getAnswer());
            }
            if(answerList.size()==ANSWER_TOTAL_NUM){
                break;
            }
        }
        Collections.shuffle(answerList);
        model.addAttribute("answerList", answerList);
        model.addAttribute("imageFileName", quizAnswer.getImgStoreName());
        return "quiz/quiz";
    }

    //post 만들기, @RequestParam

    @PostMapping("/quiz/{quizOrder}")
    public String quizPagePost(@PathVariable int quizOrder, @RequestParam("selAns") String selAns, RedirectAttributes redirectAttributes){
        QuizAnswer quizAnswer = randomQuiz.get(quizOrder);
        log.info("==================== 정답은={}", quizAnswer.getAnswer());
        log.info("====================selAns={}", selAns);
        if (quizAnswer.getAnswer().equals(selAns)){
            correct++;
        }else{
            wrong++;
        }
        log.info("===================correct num ={}", correct);
        log.info("===================wrong num ={}", wrong);

        if (quizOrder<QUIZ_TOTAL_NUM){
            redirectAttributes.addAttribute("quizOrder", quizOrder+1);
        }
        return quizOrder==QUIZ_TOTAL_NUM? "redirect:/quiz/end" : "redirect:/quiz/{quizOrder}";
    }

    @GetMapping("/quiz/end")
    public String quizEnd(Model model){
        model.addAttribute("correct", correct);
        model.addAttribute("wrong", wrong);
        return "quiz/end";
    }

    @PostMapping("/quiz/end")
    public String quizRestart(){
        correct=0;
        wrong=0;
        randomQuiz.clear();

        return "redirect:/start";
    }

    @ResponseBody
    @GetMapping("/display/{filename}")
    public Resource imageDisplay(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + fileDir+filename);
    }

    @GetMapping("/quiz/remove")
    public String removeQuiz(){
        List<QuizAnswer> all = quizRepository.findAll();
        for (QuizAnswer q : all){
            quizRepository.remove(q);


        }
        return "redirect:/quiz/create";
    }

}
