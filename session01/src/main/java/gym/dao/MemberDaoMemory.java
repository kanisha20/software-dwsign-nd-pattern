package gym.dao;

import java.util.ArrayList;
import java.util.List;

public class MemberDaoMemory implements MemberDao {

    private ArrayList<Member> members = new ArrayList<Member>();

    public MemberDaoMemory() {
        members.add(new Member(1, "Amit", "Ahmedabad", 6, 4500));
        members.add(new Member(2, "Ravi", "Ahmedabad", 3, 2500));
        members.add(new Member(3, "Neha", "Surat", 6, 4500));
        members.add(new Member(4, "Priya", "Surat", 12, 8000));
        members.add(new Member(5, "Raj", "Rajkot", 3, 2500));
        members.add(new Member(6, "Kiran", "Rajkot", 12, 8000));
    }

    public void addMember(Member m) {
        members.add(m);
    }

    public List<Member> findAll() {
        return members;
    }

    public List<Member> findByCity(String city) {
        ArrayList<Member> result = new ArrayList<Member>();

        for (Member m : members) {
            if (m.getCity().equalsIgnoreCase(city)) {
                result.add(m);
            }
        }

        return result;
    }
}
