package gym.dao;

public class Member {

    private int memberId;
    private String memberName;
    private String city;
    private int planMonths;
    private double fee;

    public Member(int memberId, String memberName, String city, int planMonths, double fee) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.city = city;
        this.planMonths = planMonths;
        this.fee = fee;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getCity() {
        return city;
    }

    public int getPlanMonths() {
        return planMonths;
    }

    public double getFee() {
        return fee;
    }
}
