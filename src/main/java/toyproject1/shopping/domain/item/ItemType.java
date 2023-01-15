package toyproject1.shopping.domain.item;

public enum ItemType {
    HEALTH("건강"), MEDICAL("의료");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
