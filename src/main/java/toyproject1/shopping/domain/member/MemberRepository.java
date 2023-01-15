package toyproject1.shopping.domain.member;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import toyproject1.shopping.domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    private static final Map<Long, Member> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    public void save(Member member){
//        member.setMemberId(++sequence);
//        store.put(member.getMemberId(), member);
        if(member.getMemberId() == null){
            em.persist(member);
        } else{
            em.merge(member);
        }
    }

    public Member findById(Long memberId){
//        Member member = store.get(memberId);
        Member member = (Member) em.createQuery("select m from Member m where m.memberId = :memberId")
                .setParameter("memberId", memberId)
                .getSingleResult();
        return member;
    }
//    public List<Member> findAll(){return new ArrayList<>(store.values());}

    public List<Member> findAll2(){
        return em.createQuery("select m from Member m")
                .getResultList();
    }

    public Member findByLoginId(String loginId){
        List<Member> members = findAll2();
        Member findMember = members.stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst()
                .orElse(null);
        return findMember;
    }


    public Map<Long, Member> getMemberStore(){
        return store;
    }
}
