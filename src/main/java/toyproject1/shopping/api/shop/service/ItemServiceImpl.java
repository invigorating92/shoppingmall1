package toyproject1.shopping.api.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject1.shopping.api.shop.repository.ItemRepository;
import toyproject1.shopping.api.shop.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    @PersistenceContext
    private EntityManager em;

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void save(Item item) {
        em.persist(item);
    }

    @Transactional
    @Override
    public void update(Item item) {
        if (itemRepository.findByItemId2(item.getItemId()) == null){
            return;
        }
        em.merge(item);
    }

}
