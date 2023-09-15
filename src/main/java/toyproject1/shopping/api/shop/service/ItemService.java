package toyproject1.shopping.api.shop.service;


import org.springframework.stereotype.Service;
import toyproject1.shopping.api.shop.domain.Item;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.web.item.ItemDTO;

@Service
public interface ItemService {

    void save(Item item);

    void update(Item item);


    default Item dtoToEntity(ItemDTO form, Member member){
        Item item = Item.builder()
                .itemId(form.getItemId())
                .itemName(form.getItemName())
                .itemType(form.getItemType())
                .price(form.getPrice())
                .quantity(form.getQuantity())
                .itemDetail(form.getItemDetail())
                .member(member)
                .build();
        return item;
    }
    default ItemDTO entityToDto(Item item){
        ItemDTO itemDTO = ItemDTO.builder()
                .ItemId(item.getItemId())
                .itemName(item.getItemName())
                .itemType(item.getItemType())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .itemDetail(item.getItemDetail())
                .build();
        return itemDTO;
    }

}
