package toyproject1.shopping.api.shop.repository;

import org.springframework.stereotype.Repository;
import toyproject1.shopping.api.shop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;

    public OrderItem findById(Long id){
        return (OrderItem) em.createQuery("select o from OrderItem o")
                .getSingleResult();
    }
}
