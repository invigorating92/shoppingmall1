package toyproject1.shopping.api.user.domain;

public enum MemberType {
    NORMAL("일반"), SELLER("판매자");

    private final String description;

    MemberType(String description) {
        this.description=description;
    }

    public String getDescription(){
        return description;
    }
}
