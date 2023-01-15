package toyproject1.shopping.quiz;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuizRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(QuizAnswer quizAnswer){
        em.persist(quizAnswer);
    }

    @Transactional
    public List<QuizAnswer> findAll(){
        return em.createQuery("select q from QuizAnswer q")
                .getResultList();
    }
    @Transactional
    public void remove(QuizAnswer quizAnswer){
        em.remove(quizAnswer);
    }
}
