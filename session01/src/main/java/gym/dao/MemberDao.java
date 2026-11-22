package gym.dao;

import java.util.List;

public interface MemberDao {
    void addMember(Member member);
    List<Member> findAll();
    List<Member> findByCity(String city);
}
