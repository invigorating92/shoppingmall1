package toyproject1.shopping.api.shop.repository;

import org.springframework.stereotype.Repository;
import toyproject1.shopping.api.shop.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;

    public void save2(Item item){
        if(item.getItemId() == null){
            em.persist(item);
        } else{
            em.merge(item);
        }
    }

    public void update(Item item){

    }

    public List<Item> findAll2(){
        return em.createQuery("select i from Item i")
                .getResultList();
    }

    public List<Item> findByMemberId2(Long memberId){
        return em.createQuery("select i from Item i where i.member.memberId = :memberId")
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public Item findByItemId2(Long itemId){
        List<Item> resultList = em.createQuery("select i from Item i where i.itemId = :itemId")
                .setParameter("itemId", itemId)
                .getResultList();

        //1. 스트림으로 처리
        Item findItem = resultList.stream().findFirst().orElse(null);

        //2. isEmpty() 사용
        //        if (resultList.isEmpty()){
//            return null;
//        }
//        Item findItem = (Item) resultList.get(0);
        return findItem;
    }

}
