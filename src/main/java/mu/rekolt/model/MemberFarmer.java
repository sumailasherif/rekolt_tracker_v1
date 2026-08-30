package mu.rekolt.model;

import java.util.Objects;

public class MemberFarmer {
    //I made these fields final to enforce immutability for registered cooperative members
    private final String memberId;
    private final String memberName;

    //I defined this constructor to bind unique member identification details upon instantiation
    public MemberFarmer(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    //I exposed this getter so other modules can access the unique member identifier
    public String getMemberId() {
        return memberId;
    }

    //I exposed this getter so other modules can retrieve the member's full name
    public String getMemberName() {
        return memberName;
    }

    //I override equals based on memberId to ensure distinct tracking across collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberFarmer)) return false;
        MemberFarmer other = (MemberFarmer) o;
        return Objects.equals(memberId, other.memberId);
    }

    //I override hashCode using memberId to maintain consistency with the equals contract
    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    //I override toString to provide standardized member output formatting
    @Override
    public String toString() {
        return memberId + " " + memberName;
    }
}