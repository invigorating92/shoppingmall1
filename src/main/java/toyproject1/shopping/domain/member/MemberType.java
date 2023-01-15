package toyproject1.shopping.domain.member;

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
