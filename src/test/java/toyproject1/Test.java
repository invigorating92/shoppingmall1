package toyproject1;

import toyproject1.shopping.api.shop.domain.ItemType;

public class Test {

    private static final String itemType1 = "MEDICAL";
    private static final String itemType2 = "HEALTH";

    @org.junit.jupiter.api.Test
    void Testt(){
        ItemType newItem1 = ItemType.MEDICAL;
        String description = newItem1.getDescription();

        System.out.println("newItem1 = " + newItem1);

        System.out.println("newItem1 = " + description);

        System.out.println("===============================");

        String newItem2 = itemType2;
        System.out.println("newItem2 = " + newItem2);

        newItem2="좋빠가";
        System.out.println("newItem2 = " + newItem2);
    }
}
