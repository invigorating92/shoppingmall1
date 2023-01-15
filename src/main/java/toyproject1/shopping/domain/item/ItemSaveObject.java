package toyproject1.shopping.domain.item;

import toyproject1.shopping.domain.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemSaveObject {

    private Map<Long, Item> itemsStore = new ConcurrentHashMap<>();
    private Long sequence = 0L;
    private static Long totalSequence = 0L;

    public Item findById(Long id){
        Item item = itemsStore.get(id);
        return item;
    }

    public List<Item> findAll(){
        return new ArrayList<>(itemsStore.values());

    }

    public Map<Long, Item> getItemsStore(){
        return itemsStore;
    }
}
